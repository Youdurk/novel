package com.baidu.novel.Controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baidu.novel.Controller.dto.UserDto;
import com.baidu.novel.common.Constant;
import com.baidu.novel.common.Result;
import com.baidu.novel.config.interceptor.AuthAccess;
import com.baidu.novel.entity.User;
import com.baidu.novel.mapper.UserMapper;
import com.baidu.novel.server.serverImpl.NovelServerImpl;
import com.baidu.novel.server.serverImpl.UserServerImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/***
 * 用户
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    UserServerImpl userServerimpl;
    @Resource
    UserMapper userMapper;
    @Resource
    NovelServerImpl novelServer;

    QueryWrapper<User> queryWrapper;
    /**
     * 查询所有数据
     * @return
     */
//    @GetMapping
//    public Result findall(){
//        return Result.success(userServerimpl.list());
//    }
    /**
     * 登录
     * @param userDto
     * @return
     */
    @PostMapping("/login")
    public Result login(@RequestBody UserDto userDto){
        String username=userDto.getUsername();
        String passowrd=userDto.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(passowrd)){
            return Result.errorr(Constant.CODE_500,"参数错误");
        }
        return Result.success(userServerimpl.login(userDto));
    }

    /***
     * 注册
     * @param userDto
     * @return
     */
    @PostMapping("/register")
    public Result register(@RequestBody UserDto userDto){
        String username=userDto.getUsername();
        String passowrd=userDto.getPassword();
        if (StrUtil.isBlank(username) || StrUtil.isBlank(passowrd)){
            return Result.errorr(Constant.CODE_500,"参数错误");
        }
        return Result.success(userServerimpl.register(userDto));
    }
    /**
     * 修改与增加
     * @param user
     * @return
     */
    @PostMapping("/insert")
    public Result insert(@RequestBody User user){
        return Result.success(userServerimpl.insert(user));
    }
    /**
     * 根据id删除
     * @param id
     * @return
     */
   @DeleteMapping("/{id}")
    public Result deleteid(@PathVariable Integer id){
      User user= userServerimpl.getById(id);
      user.setSate(0);
       return Result.success(userServerimpl.updateById(user));
    }

    /***
     * 统计每月用户增长人数
     * @return
     */
    @GetMapping("/")
 public Result ec(){
       return Result.success(userServerimpl.ec());
 }
    /***
     * 统计每月作者增长人数
     * @return
     */

 @GetMapping("/au")
 public Result au(){
        return Result.success(userServerimpl.au());
 }

 @AuthAccess
 @GetMapping("/cout")
 public Result count(){
       long us= userServerimpl.cous();
     long au= userServerimpl.couau();
     Long user=userServerimpl.count();
     long novel=novelServer.count();
     HashMap<String, Object> map = new HashMap<>();
     map.put("name",CollUtil.newArrayList("用户数","作者数","读者数","小说"));
     map.put("统计",CollUtil.newArrayList(us,au,user,novel));
     return Result.success(map);
 }
    /***
     * 统计每月读者增长人数
     * @return
     */
    @AuthAccess
    @GetMapping("/reu")
    public Result usr(){
        return Result.success(userServerimpl.usr());
    }
    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    @GetMapping("/{username}")
    public Result getuser(@PathVariable String username){
        queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
       return Result.success(userServerimpl.getOne(queryWrapper));
    }
    /***
     * 查询角色
     * @param password
     * @param username
     * @return
     */
    @AuthAccess
    @GetMapping("/users")
    public Result users(@RequestParam String password,@RequestParam String username){
      queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.eq("password",password);
        try{
            User user=userServerimpl.getOne(queryWrapper);
            return Result.success(user);
        }catch (Exception e){
            e.printStackTrace();
        }
      return null;
    }

    /***
     * 修改密码
     * @param jsb
     * @return
     */
    @AuthAccess
    @PostMapping("/upassword")
    public  Result upassword(@RequestBody JSONObject jsb){
       String username= (String) jsb.get("username");
       String password=(String) jsb.get("password");
       queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        queryWrapper.eq("password",password);
        User user=userServerimpl.getOne(queryWrapper);
        user.setPassword((String) jsb.get("newpassword"));
        System.out.printf(user.getPassword());
        System.out.printf(String.valueOf(jsb));
        return Result.success(userServerimpl.updateById(user));
    }
    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/del/bath")
    public Result deleteids(@RequestBody List<Integer> ids){
        return Result.success(userMapper.deleteBatchIds(ids));
    }
    /**
     * 读者分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public Result FindPAge(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String nickname){
         queryWrapper = new QueryWrapper<>();
        if (!"".equals(nickname)) {
        queryWrapper.like("nickname", nickname);
    }
        queryWrapper.eq("role","读者");

        queryWrapper.orderByDesc("id");
        IPage<User> page = new Page<>(pageNum,pageSize);
        return Result.success(userMapper.selectPage(page,queryWrapper));
    }

    /***
     * 作者
     * @param pageNum
     * @param pageSize
     * @param nickname
     * @return
     */
    @GetMapping("/authorpage")
    public Result FindauthorPAge(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String nickname){
         queryWrapper = new QueryWrapper<>();
        if (!"".equals(nickname)) {
            queryWrapper.like("nickname", nickname);
        }
        queryWrapper.eq("role","作者");
        queryWrapper.orderByDesc("id");
        IPage<User> page = new Page<>(pageNum,pageSize);
        return Result.success(userMapper.selectPage(page,queryWrapper));
    }

    /**
     * 统计作者
     * @return
     */
    @GetMapping("/a")
    public Result coun(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role","作者");
        return Result.success(userServerimpl.count(queryWrapper));
    }

    /***
     * 统计小说
     * @return
     */
    @GetMapping("/b")
    public Result counb(){
        return Result.success(novelServer.count());
    }
}
