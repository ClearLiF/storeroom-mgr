package com.wxy.config.security;

import com.wxy.config.response.CommonCode;
import com.wxy.exception.ExceptionCast;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : MyAuthenticationFailureHandler
 * @packageName : com.yinghuo.management.handler
 * @description : 登录认证失败类
 * @date : 2020-09-19 23:54
 **/
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException auth)
            throws IOException, ServletException {

//        res.getWriter().write(JSON.toJSONString(CommonCode.SUCCESS
//                , EnumToJson.getSerializeConfig(CommonCode.class)));
        ExceptionCast.cast(CommonCode.FAIL);
    }

}
