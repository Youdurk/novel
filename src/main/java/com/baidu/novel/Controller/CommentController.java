package com.baidu.novel.Controller;

import com.baidu.novel.common.Result;
import com.baidu.novel.config.interceptor.AuthAccess;
import com.baidu.novel.entity.Comment;
import com.baidu.novel.server.serverImpl.CommentServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Resource
    CommentServiceImpl commentService;

    /**
     * 分页
     * @param PageNum
     * @param PageSize
     * @return
     */
    @GetMapping("/page")
    public Map<String,Object> finapage(@RequestParam Integer PageNum,
                           @RequestParam Integer PageSize,@RequestParam String bname){
        PageNum=(PageNum-1)*PageSize;
        Integer total= Math.toIntExact(commentService.count());
        List<Comment> comments=commentService.finapage(PageNum,PageSize,bname);
        Map<String, Object> map = new HashMap<>();
        map.put("data",comments);
        map.put("total",total);
        return map;
    }

    /***
     * 我的评论页
     * @param PageNum
     * @param PageSize
     * @param username
     * @return
     */
    @GetMapping("/pages")
    public  Result mycommpage(@RequestParam Integer PageNum,
                                       @RequestParam Integer PageSize,
                                       @RequestParam String username){

        return Result.success(commentService.mycommt(new Page<>(PageNum,PageSize),username));
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/del/bath")
    public Result deleteids(@RequestBody List<Integer> ids){
        return Result.success(commentService.removeByIds(ids));
    }
    /***
     * 根据id删除评论
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result  del(@PathVariable Integer id){
        return Result.success(commentService.dec(id));
    }

    /**
     * 该小说所有评论
     * @param tableid
     * @return
     */
    @AuthAccess
    @GetMapping("/bcomm/{tableid}")
    public Result readcommt(@PathVariable Integer tableid){
        return Result.success(commentService.readcommt(tableid));
    }

    /***
     * 发布评论
     * @param username
     * @param comment
     * @return
     */
    @AuthAccess
    @PostMapping("/pucomm/{username}")
    public Result pucomment(@PathVariable String username,@RequestBody Comment comment){
        return  Result.success(commentService.pucomment(username,comment));
    }
}
