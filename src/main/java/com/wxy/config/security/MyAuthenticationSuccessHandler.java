package com.wxy.config.security;

import com.alibaba.fastjson.JSON;
import com.wxy.config.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : MyAuthenticationSuccessHandler
 * @packageName : com.yinghuo.management.handler
 * @description : 一般类
 * @date : 2020-09-19 23:55
 **/
@Component
@Slf4j
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        httpServletResponse.sendRedirect("/");
        httpServletResponse.getWriter().write(JSON.toJSONString(ResponseResult.success()));
    }

}