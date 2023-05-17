package com.baidu.novel.server;

import com.baidu.novel.entity.Chapters;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ChaptersServer extends IService<Chapters> {
    Page<Chapters> Finall(Page<Chapters> page,String bname);

    boolean insert(Chapters chapters, Integer bid);

    List<Chapters> catalogue(Integer id);

    Chapters reads(Integer bid, Integer sequence);

    boolean updates(Chapters chapters);

    Integer delebid(Integer bid);

    Boolean authorads(String tablename,Chapters chapters);
}
