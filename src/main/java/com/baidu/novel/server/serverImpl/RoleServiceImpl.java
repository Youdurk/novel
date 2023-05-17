package com.baidu.novel.server.serverImpl;

import com.baidu.novel.entity.Role;
import com.baidu.novel.mapper.RoleMapper;
import com.baidu.novel.server.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Override
    public boolean cde(Role role) {
        return saveOrUpdate(role);
    }
}
