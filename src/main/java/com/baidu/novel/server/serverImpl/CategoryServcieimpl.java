package com.baidu.novel.server.serverImpl;

import com.baidu.novel.entity.Category;
import com.baidu.novel.mapper.CategoryMapper;
import com.baidu.novel.server.Categoryservice;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CategoryServcieimpl extends ServiceImpl<CategoryMapper, Category> implements Categoryservice {

    @Override
    public Boolean insert(Category category) {
        return saveOrUpdate(category);
    }
}
