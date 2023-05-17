package com.baidu.novel.Controller;

import com.baidu.novel.common.Result;
import com.baidu.novel.config.interceptor.AuthAccess;
import com.baidu.novel.entity.Chapters;
import com.baidu.novel.mapper.ChaptersMapper;
import com.baidu.novel.server.serverImpl.ChaptersServerImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/***
 * 章节
 */
@RestController
@RequestMapping("/chapters")
public class ChaptersController  {
    @Resource
    private ChaptersServerImpl chaptersServer;
    @Resource
    private ChaptersMapper chaptersMapper;

    /***
     * 根据书籍id添加章节
     * @param chapters
     * @param bid
     * @return
     */
    @PostMapping("/insert/{bid}")
    public Result insert(@RequestBody Chapters chapters, @PathVariable Integer bid ){
        return Result.success( chaptersServer.insert(chapters,bid));
    }
    @PostMapping("/del/bath")
    public Result deleteids(@RequestBody List<Integer> ids){
        return Result.success(chaptersMapper.deleteBatchIds(ids));
    }

    /**
     * 根据书名添加章节
     * @param tablename
     * @return
     */
    @AuthAccess
    @PostMapping("/authoradc/{tablename}")
    public Result authorads(@PathVariable String tablename,@RequestBody Chapters chapters){
        return  Result.success(chaptersServer.authorads(tablename,chapters));
    }
    /***
     * 修改章节内容
     * @param chapters
     * @return
     */
    @PostMapping("/updatas")
    public Result updates(@RequestBody Chapters chapters){
        return Result.success(chaptersServer.updates(chapters));
    }
    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return Result.success(chaptersMapper.deleteById(id));
    }

    /**
     *书籍id删除
     * @param
     * @return
     */
    @DeleteMapping("/del/{bid}")
    public Result delebid(@PathVariable Integer bid){
        return Result.success(chaptersServer.delebid(bid));
    }
    /**
     * 章节管理分页
     * @param pageNum
     * @param pageSize
     * @param bName
     * @return
     */
    @GetMapping("/page")
    public Result page(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize,
                       @RequestParam(defaultValue = "") String bname){
        Page<Chapters> page=new Page<>(pageNum,pageSize);
        QueryWrapper<Chapters> queryWrapper = new QueryWrapper<>();
        if (!bname.equals("")){
            queryWrapper.like("bname",bname);
        }
        return Result.success(chaptersServer.Finall(page,bname));
    }

    /***
     * 根据数据id和章节顺序为1进行查询
     * @param id
     * @return
     */
    @AuthAccess
    @GetMapping("/catalogue/{id}")
    public Result read(@PathVariable Integer id){
        return Result.success(chaptersServer.catalogue(id));
    }

    /***
     * 查询章节内容
     * @param bid
     * @param sequence
     * @return
     */
   @AuthAccess
    @GetMapping("/reads")
    public Result reads(@RequestParam("bid") Integer bid, @RequestParam("sequence") Integer sequence){

        return Result.success(chaptersServer.reads(bid,sequence));
    }

}
