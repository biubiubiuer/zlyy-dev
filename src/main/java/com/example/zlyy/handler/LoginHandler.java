package com.example.zlyy.handler;

import com.alibaba.fastjson.JSON;
import com.example.zlyy.annotation.NoAuth;
import com.example.zlyy.common.R;
import com.example.zlyy.pojo.dto.UserDTO;
import com.example.zlyy.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.zlyy.util.RedisConstants.TOKEN;
import static com.example.zlyy.util.RedisConstants.TOKEN_KEY;
import static com.example.zlyy.util.StringConstants.TOKEN_PREFIX;

@Slf4j
@Component
public class LoginHandler implements HandlerInterceptor {
    
    Logger logger = LoggerFactory.getLogger(LoginHandler.class);

//    @Resource
//    private RedisMapper redisMapper;
    
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        

        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if(handlerMethod.hasMethodAnnotation(NoAuth.class)){
            return true;
        }

        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)){
            logger.error("token is blank");
            return noLoginResponse(response);
        }
        token = token.replace(TOKEN_PREFIX, "");
        boolean verify = JWTUtils.verify(token);
        if (!verify){
            logger.error("token varify error: {}", token);
            return noLoginResponse(response);
        }
//        String userJson = redisMapper.get(TOKEN_KEY + token);
        String userJson = stringRedisTemplate.opsForValue().get(TOKEN + token);
        
        logger.debug("userJson: {}", userJson);
        
        if (StringUtils.isBlank(userJson)){
            logger.error("user json is blank");
            return noLoginResponse(response);
        }
        UserDTO userDTO = JSON.parseObject(userJson, UserDTO.class);
        UserThreadLocal.put(userDTO);
        return true;
    }

    private boolean noLoginResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(R.error("未登录")));
        return false;
    }
}
