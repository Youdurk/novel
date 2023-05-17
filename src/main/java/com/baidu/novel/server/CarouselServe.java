package com.baidu.novel.server;

import com.baidu.novel.entity.Carousel;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CarouselServe extends IService<Carousel> {
    boolean insert(Carousel carousel);
}
