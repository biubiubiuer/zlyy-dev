package com.example.zlyy.mapper;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisMapper {
    
    public void set(String key, String value);
    
    public void set(String key, String value, long l, TimeUnit unit);
    
    public String get(String key);
    
    public boolean del(String key);
    
    public boolean keyIsExists(String key);

    public Set<String> keysOfPrefix(String prefix);
    
}
