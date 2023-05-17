package com.baidu.novel.mapper;

import com.baidu.novel.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface RoleMapper extends BaseMapper<Role> {
    @Select("SELECT id FROM role WHERE description=#{description}")
    Integer selectrole(@Param("description") String description);
    @Select("SELECT muenid FROM distribute WHERE roleid=#{roleid}")
    List<Integer> selectmuenid(@Param("roleid") Integer roleid);
}
