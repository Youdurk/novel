package com.baidu.novel;

import cn.hutool.core.date.DateUtil;
import com.baidu.novel.entity.Chapters;
import com.baidu.novel.entity.User;
import com.baidu.novel.mapper.BookshelfMapper;
import com.baidu.novel.mapper.ChaptersMapper;
import com.baidu.novel.mapper.UserMapper;
import com.baidu.novel.server.serverImpl.UserServerImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

@SpringBootTest
class NovelApplicationTests {

    @Resource
    UserMapper userMapper;
    @Resource
    ChaptersMapper chaptersMapper;
    @Resource
    BookshelfMapper bookshelfMapper;
    @Test
    void contextLoads() throws ParseException {

        User user=userMapper.selectById(2);
        Date date = DateUtil.parse(user.getDatatime());
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
        int year=DateUtil.year(date);
        System.out.printf(sdf1.format(date)+"\t");
        String now=DateUtil.now();
        System.out.printf(String.valueOf(year));
        System.out.printf(String.valueOf(now));
    }
    @Test
    void sd(){

        Chapters c=chaptersMapper.reads(16,1);
        String cha=c.getContent();
       String ce= cha.replace("&nbsp;","\n");
        System.out.printf(""+ce);
    }
    @Test
    void es(){
        Integer book=bookshelfMapper.getusta(1,2);
        Integer id=userMapper.getusid("user");
        System.out.printf(""+book+"+"+id);
    }

}
