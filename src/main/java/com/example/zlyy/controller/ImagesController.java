package com.example.zlyy.controller;

import com.example.zlyy.dto.R;
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
    
    @PostMapping("/hello")
    public R getIndexImages() {
        return imagesService.getIndexImages();
    }
    
    @PostMapping("/swiper")
    public R getSwiperImages() { return imagesService.getSwiperImages(); }
    
    @PostMapping("/teams")
    public R getTeamsImages() { return imagesService.getTeamsImages(); }
    
}
