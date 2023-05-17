package com.baidu.novel.Controller;

import com.baidu.novel.common.Result;
import com.baidu.novel.config.interceptor.AuthAccess;
import com.baidu.novel.entity.Category;
import com.baidu.novel.entity.Chapters;
import com.baidu.novel.entity.Comment;
import com.baidu.novel.entity.Novel;
import com.baidu.novel.mapper.*;
import com.baidu.novel.server.Novelserver;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/***
 * 小说
 */
@RestController
@RequestMapping("/novel")
public class NovelContoller {
    @Resource
    NovelMapper novelMapper;
    @Resource
    Novelserver novelserver;
    @Resource
    CommentMapper commentMapper;
    @Resource
    ChaptersMapper chaptersMapper;
    @Resource
    BookshelfMapper bookshelfMapper;
    @Resource
    CategoryMapper categoryMapper;

    /***
     * 添加或者修改
     * @param novel
     * @return
     */
  @PostMapping("/insert")
    public Result add(@RequestBody Novel novel){
      return Result.success(novelserver.add(novel));
  }
    /***
     *
     * 批量删除
     * @param ids
     * @return
     */
  @PostMapping("/del/bath")
  public Result deleteids (@RequestBody List<Integer> ids){
      for (Integer i:ids){
          commentMapper.co(i);
          chaptersMapper.de(i);
          bookshelfMapper.bok(i);
      }
      return  Result.success(novelMapper.deleteBatchIds(ids));
  }

    /***
     * 小说管理分页
     * @param pageSize
     * @param pageNum
     * @param bname
     * @return
     */
    @AuthAccess
   @GetMapping("/page")
    public Result NovelPage(@RequestParam Integer pageSize,
                            @RequestParam Integer pageNum,
                            @RequestParam(defaultValue = "") String bname){
       QueryWrapper<Novel> queryWrapper =new QueryWrapper<>();
       if (!"".equals(bname)){
           queryWrapper.like("b_name",bname);
       }
       Page<Novel> page=new Page<>(pageNum,pageSize);
    return  Result.success(novelMapper.selectPage(page,queryWrapper));
   }

    /***
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
   @AuthAccess
   @GetMapping
    public Result li(@RequestParam Integer pageSize,
                     @RequestParam Integer pageNum){
       return Result.success(novelserver.page(new Page<>(pageNum,pageSize)));
    }
    /***
     * 查询所有书籍热度最高的六本
     * @return
     */
    @AuthAccess
    @GetMapping("/popular")
    public  Result Popular(){
      return Result.success(novelserver.Popular());
    }
    /***
     * 查询玄幻小说热度最高的六本
     * @return
     */
    @AuthAccess
    @GetMapping("/popular/fantasy")
    public  Result Popularfantasy(){
        return Result.success(novelserver.Popularfantasy());
    }

    @AuthAccess
    @GetMapping("/popular/sciencefiction")
    public  Result Popularsciencefiction(){
        return Result.success(novelserver.Populasciencefiction());
    }

    @AuthAccess
    @GetMapping("/popular/xianxia")
    public  Result Popularxianxia(){
        return Result.success(novelserver.Populaxianxia());
    }
    /**
     * 热度增加
     * @param heat
     * @param id
     * @return
     */
    @AuthAccess
    @PostMapping("/heats/{heat}/{id}")
    public Result ud(@PathVariable Integer heat ,@PathVariable Integer id ){
        UpdateWrapper<Novel> updateWrapper = new UpdateWrapper<>();
        if(!heat.equals(null) && !id.equals(null)){
            updateWrapper.set("heat",heat);
            updateWrapper.eq("id",id);
        }
        return Result.success(novelserver.update(null,updateWrapper));
    }

    /**
     * 书架图书
     * @param username
     * @return
     */
    @GetMapping("/bookshlf/{username}")
    public Result bookshelf(@PathVariable String username){
        return Result.success(novelserver.bookshelf(username));
    }

    /**
     * 书架删除某本小说
     * @param username
     * @param tableid
     * @return
     */
    @DeleteMapping("/bookshlf/{username}/{tableid}")
    public Result debooksh(@PathVariable String username,@PathVariable Integer tableid){
        return Result.success(novelserver.debooksh(username,tableid));
    }

    /**
     * 添加书架
     * @param username
     * @param tableid
     * @return
     */
    @AuthAccess
    @PostMapping("/addbook/{username}/{tableid}")
    public Result addbooksh(@PathVariable String username,@PathVariable Integer tableid){
        return Result.success(novelserver.addbooksh(username,tableid));
    }

    /**
     * 查询自己的作品
     * @param username
     * @return
     */
    @GetMapping("/getUN/{username}")
    public Result getUN(@PathVariable String username){
       return Result.success(novelserver.getUN(username));
    }

    /**
     * 发布小说
     * @param username
     * @param novel
     * @return
     */
    @PostMapping("/release/{username}")
    public Result releasenovel(@PathVariable String username,@RequestBody Novel novel){
        return Result.success(novelserver.releasenovel(username,novel));
    }

    //小说类型
    @GetMapping("/classification")
    public Result classification(){
       List<String> categoryList= categoryMapper.getdesc();
        ArrayList<String> list = new ArrayList<>();
        for (String s:categoryList){
            if (s!=null){
                list.add(s);
            }
        }
        return Result.success(list);
    }
    /**
     * 根据id查询
     * @param id
     * @return
     */
    @AuthAccess
    @GetMapping("/selectid/{id}")
    public Result selectid(@PathVariable Integer id){
        return Result.success(novelMapper.selectById(id));
    }

    /**
     * 玄幻类全部小说
     * @param pageSize
     * @param pageNum
     * @return
     */
    @AuthAccess
    @GetMapping("/page/fantasy")
    public Result fantasyPage(@RequestParam Integer pageSize,
                              @RequestParam Integer pageNum
    ){
        QueryWrapper<Novel> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("b_type","玄幻");
        Page<Novel> page=new Page<>(pageNum,pageSize);
        return  Result.success(novelMapper.selectPage(page,queryWrapper));
    }
    /**
     * 科幻类全部小说
     * @param pageSize
     * @param pageNum
     * @return
     */
    @AuthAccess
    @GetMapping("/page/sciencefiction")
    public Result sciencefiction(@RequestParam Integer pageSize,
                              @RequestParam Integer pageNum
    ){
        QueryWrapper<Novel> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("b_type","科幻");
        Page<Novel> page=new Page<>(pageNum,pageSize);
        return  Result.success(novelMapper.selectPage(page,queryWrapper));
    }
    /**
     * 仙侠类全部小说
     * @param pageSize
     * @param pageNum
     * @return
     */
    @AuthAccess
    @GetMapping("/page/xianxia")
    public Result xianxia(@RequestParam Integer pageSize,
                                 @RequestParam Integer pageNum
    ){
        QueryWrapper<Novel> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("b_type","仙侠");
        Page<Novel> page=new Page<>(pageNum,pageSize);
        return  Result.success(novelMapper.selectPage(page,queryWrapper));
    }

}
