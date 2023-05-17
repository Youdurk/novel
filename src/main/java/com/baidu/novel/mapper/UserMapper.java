package com.baidu.novel.mapper;

import com.baidu.novel.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper extends BaseMapper<User> {
    @Select("SELECT id FROM n_user WHERE username=#{username}")
    Integer getusid(@Param("username") String username);
}
