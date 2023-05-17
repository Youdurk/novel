package com.baidu.novel.mapper;

import com.baidu.novel.entity.Chapters;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ChaptersMapper extends BaseMapper<Chapters> {
    Page<Chapters> Finall(Page<Chapters> page,String bname);

    List<Chapters> catalogue(Integer id);
    Chapters reads(Integer bid,Integer sequence);


    List<Chapters> selectbid(Integer bid);

    @Select("SELECT  sequence FROM b_chapters WHERE B_id=#{id}")
    List<Integer> getcharid(Integer id);
    @Delete("DELETE FROM b_chapters WHERE B_id=#{tableid}")
    Boolean de(@Param("tableid") Integer tableid);
}
