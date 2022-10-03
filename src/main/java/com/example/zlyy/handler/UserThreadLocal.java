package com.example.zlyy.handler;

import com.example.zlyy.pojo.dto.UserDTO;

public class UserThreadLocal {

    private static final ThreadLocal<UserDTO> LOCAL = new ThreadLocal<>();

    public static void put(UserDTO userDTO){
        LOCAL.set(userDTO);
    }

    public static UserDTO get(){
        return LOCAL.get();
    }

    public static void remove(){
        LOCAL.remove();
    }
}
