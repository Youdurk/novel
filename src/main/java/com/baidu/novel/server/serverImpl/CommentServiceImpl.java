package com.baidu.novel.server.serverImpl;


import com.baidu.novel.entity.Comment;
import com.baidu.novel.mapper.CommentMapper;
import com.baidu.novel.mapper.UserMapper;
import com.baidu.novel.server.CommentService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    CommentMapper commentMapper;
    @Resource
    UserMapper userMapper;
    @Override
    public List<Comment> finapage(Integer pageNum, Integer pageSize,String bname) {
        return commentMapper.finapage(pageNum,pageSize,bname);
    }

    @Override
    public Boolean dec(Integer id) {
        if(id!=null){
            return removeById(id);
        }else{
            return false;
        }
    }

    @Override
    public List<Comment> readcommt(Integer tableid) {
        return commentMapper.readcommt(tableid);
    }

    @Override
    public Boolean pucomment(String username, Comment comment) {
        try {
            Integer userid= userMapper.getusid(username);
            if (userid!=null){
                comment.setUserid(userid);
                return save(comment);
            }else {
                return  false;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       return false;

    }

    @Override
    public Page<Comment> mycommt(Page<Comment> page,String username) {
        try {
            Integer userid= userMapper.getusid(username);
            if (userid!=null){

              return commentMapper.mycommt(page,userid) ;
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
