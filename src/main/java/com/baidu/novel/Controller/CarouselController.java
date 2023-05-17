package com.baidu.novel.Controller;

import com.baidu.novel.common.Result;
import com.baidu.novel.config.interceptor.AuthAccess;
import com.baidu.novel.entity.Carousel;

import com.baidu.novel.mapper.CarouselMapper;
import com.baidu.novel.server.serverImpl.CarouselServeImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/***
 * 轮播图管理
 */
@RestController
@RequestMapping("/carousel")
public class CarouselController {
    @Resource
    private CarouselServeImpl carouselServe;
    @Resource
    private CarouselMapper carouselMapper;

    /***
     * 插入和修改
     * @param carousel
     * @return
     */
    @PostMapping("/insert")
     public Result insert(@RequestBody Carousel carousel){
         return Result.success(carouselServe.insert(carousel));
     }

    /**
     * 批量删除
     * @param ids
     * @return
     */
     @PostMapping("/del/ids")
     public Result deleids(@RequestBody List<Integer> ids){
        return  Result.success(carouselServe.removeBatchByIds(ids));
     }
    /***
     * 删除
     * @param id
     * @return
     */
     @DeleteMapping("/{id}")
     public Result deleid(@PathVariable Integer id){
        return Result.success(carouselMapper.deleteById(id));
     }

     @AuthAccess
     @GetMapping("/list")
     public  Result fina(){
         return Result.success(carouselServe.list());
     }
    /***
     * 分页
     * @param pageNum
     * @param pageSize
     * @param scopes
     * @return
     */
     @GetMapping("/page")
    public Result carouselPAge(@RequestParam Integer pageNum,
                               @RequestParam Integer pageSize,
                               @RequestParam(defaultValue = "") Integer scopes){
         QueryWrapper<Carousel> queryWrapper =new QueryWrapper<>();
         if(scopes !=null){
             queryWrapper.like("scope",scopes);
         }
         Page<Carousel> page=new Page<>(pageNum,pageSize);
         return  Result.success(carouselMapper.selectPage(page,queryWrapper));
     }
}
