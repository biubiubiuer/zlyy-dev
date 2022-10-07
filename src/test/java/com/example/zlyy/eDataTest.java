package com.example.zlyy;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class eDataTest {
    public static void main(String[] args) {
        
        Logger logger = LoggerFactory.getLogger(eDataTest.class);
        
        String eData = "e0Ef6lAnI2GFeFiJWouV/UfiJirOByCTmpttCft/5jHOajDqXJUJg+2S2d5UXTdYJ4hKLynaS4Usq25gp/EGDngkJEzcqE1CzFwNWso5SCXVqNWE++KwiYPQetzKh6MtWjK1Q16XBE4QGzDgHvUZ8lEUbc7UtPoiS/49zZMTvzsRm/jhWzE6w1UJbJpGxUvVcxamC/YubkqSKchPhaVc4qLNDlBLZFIxYHIsfuJ4q5Ra0Rvu2s2WaD51kn9jcICTgel5GJhovEQmFv7+XDgJCZi2XeiwWHF1p/dNBmKpSrz3x++iWwVxr7NsZ+ygB5kEWtwvbySzwH7Ie0lYfR42wmaZhyGsM4KkTMYZWiGk0S8JYirpzoApLYW8Cm1LcUYkskp1aY/mJQmY+PrcE6SsSg==";
        
        logger.debug("eData length: {}", eData.length());
        
    }
}
