package com.example.config;

import com.example.common.exception.ServiceException;
import com.example.common.untils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Date;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        // 2. 从请求头中获取令牌
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is missing");
            return false;
        }

        Claims claims;
        try {
            claims = JwtUtil.parseJWT(secretKey, token);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
            return false;
        }

        // 3. 验证令牌是否过期
        if (claims.getExpiration().before(new Date())) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is expired");
            return false;
        }

        // 4. 将用户信息设置到请求属性中
        request.setAttribute("email", claims.get("email"));
        return true;
    }

}