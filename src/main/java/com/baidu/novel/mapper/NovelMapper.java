package com.baidu.novel.mapper;

import com.baidu.novel.entity.Novel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NovelMapper  extends BaseMapper<Novel> {
    List<Novel> Popular();

    List<Novel> getun(Integer userid);

    Integer getid(String tablename);

    List<Novel> Popularfantasy(String btype);
//    @Select("SELECT * FROM b_table WHERE b_name=#{bName}")
//    Integer getnovel(String bName);
}
