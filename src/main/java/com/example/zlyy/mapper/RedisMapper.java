package com.example.zlyy.mapper;

public interface RedisMapper {
    
    public void set(String key, String value);
    
    public String get(String key);
    
    public boolean del(String key);
}
