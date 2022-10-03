package com.example.zlyy.controller;

import com.example.zlyy.annotation.NoAuth;
import com.example.zlyy.common.R;
import com.example.zlyy.service.ImagesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/images")
@ResponseBody
public class ImagesController {
    
    @Resource
    private ImagesService imagesService;

    @NoAuth
    @PostMapping(value = "/hello", produces={"application/json;charset=UTF-8"})
    public R getIndexImages() {
        return imagesService.getIndexImages();
    }

    @NoAuth
    @PostMapping(value = "/swiper", produces={"application/json;charset=UTF-8"})
    public R getSwiperImages() { return imagesService.getSwiperImages(); }

    @NoAuth
    @PostMapping(value = "/teams", produces={"application/json;charset=UTF-8"})
    public R getTeamsImages() { return imagesService.getTeamsImages(); }
    
}
