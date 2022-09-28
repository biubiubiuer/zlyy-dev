package com.example.zlyy.mapper.impl;

import com.example.zlyy.mapper.RedisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisMapperImpl implements RedisMapper {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean del(String key) {
        return stringRedisTemplate.delete(key);
    }
}
