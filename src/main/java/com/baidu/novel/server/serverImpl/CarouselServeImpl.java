package com.baidu.novel.server.serverImpl;

import com.baidu.novel.entity.Carousel;
import com.baidu.novel.mapper.CarouselMapper;
import com.baidu.novel.server.CarouselServe;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class CarouselServeImpl extends ServiceImpl<CarouselMapper, Carousel> implements CarouselServe {
    @Override
    public boolean insert(Carousel carousel) {
        if (carousel.getId() ==null){
            return save(carousel);
        }else{
            return updateById(carousel);
        }

    }

}
