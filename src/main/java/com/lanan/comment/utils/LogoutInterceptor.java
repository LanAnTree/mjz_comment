package com.lanan.comment.utils;

import cn.hutool.core.util.StrUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.lanan.comment.utils.RedisConstants.LOGIN_USER_KEY;

/**
 * @author LanAn
 * @date 2022/10/12-17:22
 */
public class LogoutInterceptor implements HandlerInterceptor {
    private StringRedisTemplate stringRedisTemplate;

    public LogoutInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取请求头中的token
        String token = request.getHeader("authorization");
        if (!StrUtil.isBlank(token)) {
            // 2.基于TOKEN获取redis中的用户
            String key  = LOGIN_USER_KEY + token;
            stringRedisTemplate.delete(key);
        }
        // 3.删除
        UserHolder.removeUser();
        response.sendRedirect("/login.html");
        return false;
    }
}
