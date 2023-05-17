package com.baidu.novel.mapper;


import com.baidu.novel.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> finapage( Integer PageNum, Integer PageSize,String bname);

    List<Comment> readcommt(@Param("tableid") Integer tableid);

    Page<Comment> mycommt(Page<Comment> page,Integer userid);
    @Delete("DELETE FROM comment WHERE tableid=#{tableid}")
    Boolean co(@Param("tableid") Integer tableid);

}
