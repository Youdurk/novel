package com.baidu.novel.server;

import com.baidu.novel.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

public interface RoleService extends IService<Role> {
    boolean cde(Role role);

}
