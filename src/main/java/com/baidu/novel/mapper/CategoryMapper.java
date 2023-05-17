package com.baidu.novel.mapper;

import com.baidu.novel.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CategoryMapper extends BaseMapper<Category> {

    @Select("SELECT description FROM category ")
    List<String> getdesc();
}
