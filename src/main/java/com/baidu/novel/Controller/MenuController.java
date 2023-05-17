package com.baidu.novel.Controller;

import com.baidu.novel.common.Result;
import com.baidu.novel.entity.Muen;
import com.baidu.novel.mapper.MuenMapper;
import com.baidu.novel.server.serverImpl.MuenServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.collections4.Get;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/muen")
public class MenuController {

    @Resource
    MuenMapper muenMapper;
    @Resource
    MuenServiceImpl muenService;

    @PostMapping("/ins")
    public Result menuinsert(@RequestBody Muen muen){
        return Result.success(muenService.menuinsert(muen));
    }
    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/d/{id}")
    public Result deleteid(@PathVariable Integer id){
        return Result.success(muenMapper.deleteById(id));
    }
    /***
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/del/bath")
    public Result deleteids (@RequestBody List<Integer> ids){
        return  Result.success(muenMapper.deleteBatchIds(ids));
    }
    @GetMapping("/ids")
    public Result finall(){
        return Result.success(muenService.list().stream().map(Muen::getId));
    }
    /**
     * 关系绑定
     * @param roleid
     * @param muenids
     * @return
     */
    @PostMapping("/role/{roleid}")
    public Result setrolemuen(@PathVariable Integer roleid,@RequestBody List<Integer> muenids ){
        muenService.serrolemuen(roleid,muenids);
        return Result.success();
    }
    /**
     * 查询全部muen表
     * @return
     */
    @GetMapping
    public Result allmenu(){
    return Result.success(muenService.allmmenu());
    }

    /**
     *根据roleid查询muenid
     * @param roleid
     * @return
     */
    @GetMapping("/muen/{roleid}")
    public Result getmuenid(@PathVariable Integer roleid){
        return Result.success(muenService.getmuenid(roleid));
    }

}
//    /***
//     * 分页
//     * @param pageNum
//     * @param pageSize
//     * @return
//     */
//    @GetMapping("/page")
//    public Result menuPage(@RequestParam Integer pageNum,
//                            @RequestParam Integer pageSize){
//        return Result.success(muenService.page(new Page<>(pageNum,pageSize)));}
//}
