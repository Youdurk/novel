package com.baidu.novel.server;

import com.baidu.novel.entity.Novel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface Novelserver extends IService<Novel> {
    Boolean add(Novel novel);

    List<Novel> Popular();

    List<Novel> bookshelf(String username);

    Boolean debooksh(String username,Integer tableid);

    int addbooksh(String username, Integer tableid);

    Boolean releasenovel(String username, Novel novel);

    List<Novel> getUN(String username);

    List<Novel> Popularfantasy();

    List<Novel> Populasciencefiction();

    List<Novel> Populaxianxia();
}
