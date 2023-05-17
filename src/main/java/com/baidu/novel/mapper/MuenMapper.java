package com.baidu.novel.mapper;

import com.baidu.novel.entity.Distribute;
import com.baidu.novel.entity.Muen;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MuenMapper extends BaseMapper<Muen> {

    List<Integer> getmuenid(Integer roleid);
}
