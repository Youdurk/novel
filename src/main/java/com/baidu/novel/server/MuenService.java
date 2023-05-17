package com.baidu.novel.server;

import com.baidu.novel.entity.Muen;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface MuenService extends IService<Muen> {
    boolean menuinsert(Muen muen);

    List<Muen> allmmenu();

    List<Integer> getmuenid(Integer roleid);

    List<Muen> getrolemuen(Integer roleid);

    void serrolemuen(Integer roleid, List<Integer> muenids);
}
