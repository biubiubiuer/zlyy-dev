package com.example.zlyy.handler;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.zlyy.annotation.NotAdmin;
import com.example.zlyy.common.R;
import com.example.zlyy.mapper.AdminMapper;
import com.example.zlyy.mapper.UserMapper;
import com.example.zlyy.pojo.Admin;
import com.example.zlyy.pojo.User;
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
import static com.example.zlyy.util.StringConstants.TOKEN_PREFIX;

@Slf4j
@Component
public class AdminHandler implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(AdminHandler.class);

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private AdminMapper adminMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        

        if (!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if(handlerMethod.hasMethodAnnotation(NotAdmin.class)){
            return true;
        }

        String token = request.getHeader("Authorization").replace(TOKEN_PREFIX, "");
        
        boolean verify = JWTUtils.verify(token);
        if (!verify){
            logger.error("token varify error: {}", token);
            return noAdminResponse(response);
        }

        String userJson = stringRedisTemplate.opsForValue().get(TOKEN + token);

        logger.debug("userJson: {}", userJson);

        if (StringUtils.isBlank(userJson)){
            logger.error("user json is blank");
            return noAdminResponse(response);
        }
        UserDTO userDTO = JSON.parseObject(userJson, UserDTO.class);

        String openId = userDTO.getOpenId();

        Admin admin = adminMapper.selectOne(Wrappers.<Admin>lambdaQuery().eq(Admin::getOpenId, openId).last("limit 1"));
        
        if (admin == null) {
            return noAdminResponse(response);
        }
        
        return true;
    }

    private boolean noAdminResponse(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(R.error("您不是管理员")));
        return false;
    }
}
