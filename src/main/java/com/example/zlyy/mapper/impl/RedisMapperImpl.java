package com.example.zlyy.mapper.impl;

import com.example.zlyy.mapper.RedisMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisMapperImpl implements RedisMapper {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    
    @Override
    public void set(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, String value, long l, TimeUnit unit) {
        stringRedisTemplate.opsForValue().set(key, value, l, unit);
    }

    @Override
    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean del(String key) {
        return stringRedisTemplate.delete(key);
    }

    @Override
    public boolean keyIsExists(String key) {
        return stringRedisTemplate.hasKey(key);
    }

    @Override
    public Set<String> keysOfPrefix(String prefix) {
        return stringRedisTemplate.keys(prefix + "*");
    }

}
