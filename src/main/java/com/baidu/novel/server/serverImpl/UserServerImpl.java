package com.baidu.novel.server.serverImpl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.log.Log;
import com.baidu.novel.Controller.dto.UserDto;
import com.baidu.novel.common.Constant;
import com.baidu.novel.entity.Muen;
import com.baidu.novel.entity.User;
import com.baidu.novel.exception.ServiceException;
import com.baidu.novel.mapper.RoleMapper;
import com.baidu.novel.mapper.UserMapper;
import com.baidu.novel.server.UserServer;
import com.baidu.novel.utils.TokenUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;

import java.util.*;

@Service
public class UserServerImpl extends ServiceImpl<UserMapper, User>  implements UserServer {
    private static final Log LOG = Log.get();
    @Resource
    RoleMapper roleMapper;
    @Resource
    MuenServiceImpl muenService;
    @Resource
    UserMapper userMapper;
    @Override
    public Boolean insert(User user) {
        if (user.getId() ==null ){
            return save(user);
        }else {
            return updateById(user);
        }
    }

    @Override
    public UserDto login(UserDto userDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDto.getUsername());
        queryWrapper.eq("password", userDto.getPassword());
        try {
            User one = getOne(queryWrapper);
            if (one != null) {
                if (one.getSate()==1){
                    BeanUtil.copyProperties(one,userDto,true);
                    String token= TokenUtils.genToken(one.getId().toString(),one.getPassword());
                    userDto.setToken(token);
                    String role=one.getRole();

                    Integer roleid=roleMapper.selectrole(role);
                    List<Muen> muenids=muenService.getrolemuen(roleid);
                    userDto.setMuen(muenids);
                    return userDto;
                }else{
                    throw new ServiceException(Constant.CODE_600, "该账号已被冻结");
                }

            } else {
                throw new ServiceException(Constant.CODE_600, "用户名不存在");
            }
        } catch (Exception e) {
            LOG.error(e);
        }
            return null;
    }

    @Override
    public User register(UserDto userDto) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", userDto.getUsername());
        try{
            User one=getOne(queryWrapper);
            if (one==null){
                one = new User();
                BeanUtil.copyProperties(userDto,one,true);
                one.setDatatime(DateUtil.now());
          one.setSate(1);
                save(one);
                return one;
            }else{
              throw new ServiceException(Constant.CODE_600,"用户已存在");
            }
        }catch (Exception e){
            LOG.error(e);
        }
       return null;
    }

    @Override
    public Map ec() {
        List<User> list=list();
        Map<String,Object> map=new HashMap<>();
        int January=0;
        int February=0;
        int March=0;
        int April=0;
        int May=0;
        int June=0;
        int July=0;
        int August=0;
        int September=0;
        int October=0;
        int November=0;
        int December=0;
        List<Integer> list1=new ArrayList<>();
        for (User user:list){
            Date date= DateUtil.parse(user.getDatatime());
            Date date1=DateUtil.date();
            SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
            Integer year=DateUtil.year(date);
            Integer year1=DateUtil.year(date1);
            String te=sdf2.format(date);
            if (year1.equals(year)){
                switch (te){
                    case "01":
                        January++;
                       break;
                    case "02":
                        February++;
                       break;
                    case "03":
                        March++;
                     break;
                    case "04":
                        April++;
                        break;
                    case "05":
                        May++;
                        break;
                    case "06":
                        June++;
                        break;
                    case "07":
                        January++;
                        break;
                    case "08":
                        August++;
                        break;
                    case "09":
                        September++;
                        break;
                    case "10":
                        October++;
                        break;
                    case "11":
                        November++;
                        break;
                    case "12":
                        December++;
                        break;
                }

            }else{
                return null;
            }

        }
        list1.add(January);
        list1.add(February);
        list1.add(March);
        list1.add(April);
        list1.add(May);
        list1.add(June);
        list1.add(July);
        list1.add(August);
        list1.add(September);
        list1.add(October);
        list1.add(November);
        list1.add(December);
        map.put("y",list1);
        map.put("x",CollUtil.newArrayList("一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"));
        return map;
    }

    @Override
    public Map au() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role","作者");
       List<User> users= list(queryWrapper);
        Map<String,Object> map=new HashMap<>();
        int January=0;
        int February=0;
        int March=0;
        int April=0;
        int May=0;
        int June=0;
        int July=0;
        int August=0;
        int September=0;
        int October=0;
        int November=0;
        int December=0;
        List<Integer> list1=new ArrayList<>();
        for (User use:users){
            Date date= DateUtil.parse(use.getDatatime());
            Date date1=DateUtil.date();
            SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
            Integer year=DateUtil.year(date);
            Integer year1=DateUtil.year(date1);
            String te=sdf2.format(date);
            if (year1.equals(year)){
                switch (te){
                    case "01":
                        January++;
                        break;
                    case "02":
                        February++;
                        break;
                    case "03":
                        March++;
                        break;
                    case "04":
                        April++;
                        break;
                    case "05":
                        May++;
                        break;
                    case "06":
                        June++;
                        break;
                    case "07":
                        January++;
                        break;
                    case "08":
                        August++;
                        break;
                    case "09":
                        September++;
                        break;
                    case "10":
                        October++;
                        break;
                    case "11":
                        November++;
                        break;
                    case "12":
                        December++;
                        break;
                }

            }else{
                return null;
            }

        }
        list1.add(January);
        list1.add(February);
        list1.add(March);
        list1.add(April);
        list1.add(May);
        list1.add(June);
        list1.add(July);
        list1.add(August);
        list1.add(September);
        list1.add(October);
        list1.add(November);
        list1.add(December);
        map.put("y",list1);
        map.put("name","作者");
        return map;
    }

    @Override
    public Map usr() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role","读者");
        List<User> list=list(queryWrapper);
        Map<String,Object> map=new HashMap<>();
        int January=0;
        int February=0;
        int March=0;
        int April=0;
        int May=0;
        int June=0;
        int July=0;
        int August=0;
        int September=0;
        int October=0;
        int November=0;
        int December=0;
        List<Integer> list1=new ArrayList<>();
        for (User use:list){
            Date date= DateUtil.parse(use.getDatatime());
            Date date1=DateUtil.date();
            SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
            Integer year=DateUtil.year(date);
            Integer year1=DateUtil.year(date1);
            String te=sdf2.format(date);
            if (year1.equals(year)){
                switch (te){
                    case "01":
                        January++;
                        break;
                    case "02":
                        February++;
                        break;
                    case "03":
                        March++;
                        break;
                    case "04":
                        April++;
                        break;
                    case "05":
                        May++;
                        break;
                    case "06":
                        June++;
                        break;
                    case "07":
                        January++;
                        break;
                    case "08":
                        August++;
                        break;
                    case "09":
                        September++;
                        break;
                    case "10":
                        October++;
                        break;
                    case "11":
                        November++;
                        break;
                    case "12":
                        December++;
                        break;
                }

            }else{
                return null;
            }

        }
        list1.add(January);
        list1.add(February);
        list1.add(March);
        list1.add(April);
        list1.add(May);
        list1.add(June);
        list1.add(July);
        list1.add(August);
        list1.add(September);
        list1.add(October);
        list1.add(November);
        list1.add(December);
        map.put("y",list1);
        map.put("name","读者");
        return map;
    }
    public long couau(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role","作者");
        return count(queryWrapper);
    }
    public long cous(){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role","读者");
        return count(queryWrapper);
    }
}
