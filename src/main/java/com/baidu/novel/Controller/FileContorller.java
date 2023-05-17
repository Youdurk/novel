package com.baidu.novel.Controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baidu.novel.common.Result;
import com.baidu.novel.entity.Files;
import com.baidu.novel.mapper.FileMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.Word6Extractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTMLWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/***
 * 文件
 */
@RestController
@RequestMapping("/file")
public class FileContorller {

    @Value("${files.upload.path}")
    private String fileuploadpath;
    @Value("${server.ip}")
    private String serverip;

    @Resource
    private FileMapper fileMapper;
    @PostMapping("/upload")
    public String upload(@RequestParam MultipartFile file) throws IOException {
        String originalFilename=file.getOriginalFilename();
        //获取文件名和扩展名
        String  types=FileUtil.extName(originalFilename);
        System.out.println(types);
        long size=file.getSize();
        //定义唯一识别码
        String uuid=IdUtil.fastSimpleUUID();
        String fileuuid= uuid+ StrUtil.DOT+types;
        File uploadfilepath=new File(fileuploadpath+fileuuid);
        //判断配置的文件目录是否存在，不存在则创建一个新的文件目录
        File parentFile=uploadfilepath.getParentFile();
        if(!parentFile.exists()){
                parentFile.mkdir();
        }
        String Url;
//        获取文件的md5
        String  md5= SecureUtil.md5(file.getInputStream());
        //从数据库查询是否相同的记录
        Files dbfiles = getFilesmd5(md5);
        //当文件存在的时候再获取文件的md5
        if (dbfiles !=null){
            //获取文件的URl
            Url=dbfiles.getUrl();
        }else{
            //上传文件到磁盘
            file.transferTo(uploadfilepath);
            file.getInputStream();
            //若数据库不存在重复文件，则不删除刚才上传的文件
            Url="http://"+serverip+":9090/file/"+fileuuid;
            //存储数据库
            Files savefile = new Files();
            savefile.setName(originalFilename);
            savefile.setTypes(types);
            savefile.setSize(size/1024);
            savefile.setUrl(Url);
            savefile.setMd5(md5);
            fileMapper.insert(savefile);
        }

        return Url;
    }

    /**
     *
     * @param md5
     * @return
     */
    private Files getFilesmd5(String md5){
        //查询文件的md5是否存在
        QueryWrapper<Files> QueryWrapper = new QueryWrapper<>();
        QueryWrapper.eq("md5",md5);
        List<Files> files = fileMapper.selectList(QueryWrapper);
        return files.size() ==0 ?null:files.get(0);
    }
    /**
     * 文件下载路劲
     * @param fileuuid
     * @param response
     * @throws IOException
     */
    @GetMapping("/{fileuuid}")
    public void download(@PathVariable String fileuuid, HttpServletResponse response) throws IOException {
        // 根据文件的唯一标识码获取文件
        File uploadfilepath = new File(fileuploadpath +fileuuid);
        // 设置输出流的格式
        ServletOutputStream os = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileuuid, "UTF-8"));
        response.setContentType("application/octet-stream");
        // 读取文件的字节流
        os.write(FileUtil.readBytes(uploadfilepath));
        os.flush();
        os.close();

    }

    /**
     * 读取word文档内容
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/uword")
    public Result upword(@RequestParam MultipartFile file) throws IOException {
      InputStream stream=file.getInputStream();
      String OriginalFilename= file.getOriginalFilename();
      String type=FileUtil.extName(OriginalFilename);
        if (type.equals("doc")){
            HWPFDocument hwpfDocument=new HWPFDocument(stream);
            WordExtractor wordExtractor =new WordExtractor(hwpfDocument);
            String res=wordExtractor.getText();
            return Result.success(res);
        }else if (type.equals("docx")){
            XWPFDocument document = new XWPFDocument(stream);
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            String docx=extractor.getText();
            return Result.success(docx);
        }else{
            System.out.printf("ddddd");
            return null;
        }


    }

}
