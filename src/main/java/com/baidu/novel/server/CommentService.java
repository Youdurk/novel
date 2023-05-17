package com.baidu.novel.server;


import com.baidu.novel.entity.Comment;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;


import java.util.List;


public interface CommentService extends IService<Comment> {


    List<Comment> finapage(Integer PageNum, Integer PageSize,String bname);

    Boolean dec(Integer id);

    List<Comment> readcommt(Integer tableid);

    Boolean pucomment(String username, Comment comment);

    Page<Comment> mycommt(Page<Comment> tPage, String username);

}
