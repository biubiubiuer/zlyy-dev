package com.example.zlyy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.zlyy.dto.R;

public interface ImagesService {


    R getIndexImages();

    R getSwiperImages();

    R getTeamsImages();
}
