package com.baidu.novel.server;

import com.baidu.novel.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

public interface Categoryservice extends IService<Category> {
    Boolean insert(Category category);
}
