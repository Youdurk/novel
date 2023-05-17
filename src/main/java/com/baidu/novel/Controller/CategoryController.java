package com.baidu.novel.Controller;

import com.baidu.novel.common.Result;
import com.baidu.novel.config.interceptor.AuthAccess;
import com.baidu.novel.entity.Category;
import com.baidu.novel.entity.Novel;
import com.baidu.novel.server.serverImpl.CategoryServcieimpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Resource
    CategoryServcieimpl  categoryServcie;
    @AuthAccess
    @GetMapping
    public Result finall(){
        return Result.success(categoryServcie.list());
    }

    /**
     * 添加或者删除
     * @param category
     * @return
     */
    @PostMapping("/insert")
    public Result insert(@RequestBody Category category){
        return Result.success(categoryServcie.insert(category));
    }
    /***
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){ return Result.success(categoryServcie.removeById(id));}

    /***
     * 分页
     * @param pageSize
     * @param pageNum
     * @return
     */
    @GetMapping("/page")
    public Result fantasyPage(@RequestParam Integer pageSize,
                              @RequestParam Integer pageNum
    ){
        return  Result.success(categoryServcie.page(new Page<>(pageNum,pageSize)));
    }
}
