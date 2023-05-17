package com.baidu.novel.mapper;

import com.baidu.novel.entity.BookShelf;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

public interface BookshelfMapper extends BaseMapper<BookShelf> {
    @Select("SELECT tableid FROM bookshelf WHERE userid=#{userid}")
    List<Integer> getableid(@Param("userid") Integer userid);
    @Delete("DELETE FROM bookshelf WHERE userid=#{userid} AND tableid=#{tableid}")
    Boolean bookde(@Param("userid") Integer userid,@Param("tableid") Integer tableid);
    @Select("SELECT  * FROM bookshelf WHERE userid=#{userid} AND tableid=#{tableid}")
    Integer getusta(Integer userid, Integer tableid);
    @Delete("DELETE FROM bookshelf WHERE tableid=#{tableid}")
    Boolean bok(@Param("tableid") Integer tableid);
}
