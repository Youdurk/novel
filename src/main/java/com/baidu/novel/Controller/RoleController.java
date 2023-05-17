package com.baidu.novel.Controller;

import com.baidu.novel.common.Result;
import com.baidu.novel.entity.Role;
import com.baidu.novel.mapper.RoleMapper;
import com.baidu.novel.server.serverImpl.RoleServiceImpl;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Resource
    RoleMapper roleMapper;
    @Resource
    RoleServiceImpl roleService;


    @PostMapping("/insert")
    public Result insert(@RequestBody Role role){
        return Result.success(roleService.cde(role));
    }
    /***
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result del(@PathVariable Integer id){
        return Result.success(roleMapper.deleteById(id));
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/del/ids")
    public  Result ids(@RequestBody List<Integer> ids){
        return Result.success(roleMapper.deleteBatchIds(ids));
    }

    /**
     * 分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result page(@RequestParam Integer pageNum,
                       @RequestParam Integer pageSize){
        return Result.success(roleService.page(new Page<>(pageNum,pageSize)));
    }

}
