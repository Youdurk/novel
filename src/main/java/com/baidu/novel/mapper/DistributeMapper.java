package com.baidu.novel.mapper;

import com.baidu.novel.entity.Distribute;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

public interface DistributeMapper extends BaseMapper<Distribute> {
    @Delete("DELETE FROM distribute WHERE roleid=#{roleid}")
    Integer deletroleid(@Param("roleid") Integer roleid);
}
