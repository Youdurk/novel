package com.baidu.novel.server;

import com.baidu.novel.Controller.dto.UserDto;
import com.baidu.novel.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface UserServer extends IService<User> {
    Boolean insert(User user);

    UserDto login(UserDto userDto);

    User register(UserDto userDto);

    Map ec();

    Map au();

    Map usr();
}
