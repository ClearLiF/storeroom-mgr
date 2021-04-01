package com.wxy.config.security;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.wxy.config.response.ResponseResult;
import com.wxy.model.SrAdmin;
import com.wxy.model.response.AdminCode;
import com.wxy.service.AdminService;
import com.wxy.utils.JwtUtil;
import com.wxy.utils.StaticMethodGetBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : JWTAuthorizationFilter
 * @packageName : com.yinghuo.management.handler
 * @description : 如果有jwt时
 * @date : 2020-09-22 22:52
 **/
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private AdminService adminService = StaticMethodGetBean.getBean(AdminService.class);

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String tokenHeader = request.getHeader(JwtUtil.TOKEN_HEADER);
        // 如果请求头中没有Authorization信息则直接放行了
        if (tokenHeader == null) {

            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            chain.doFilter(request, response);
            return;
        }
        // 如果请求头中有token，则进行解析，并且设置认证信息
//        if (!JwtUtil.isExpiration( tokenHeader.replace(JwtUtil.TOKEN_PREFIX, ""))){
//            response.getWriter().write(JSON.toJSONString(new ResponseResult(AdminCode.TIME_OUT)));
//            return;
//        } User user=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //我们需要在程序中获取当前用户的相关信息，比如最常见的是获取当前登录用户的用户名。在程序的任何地方，通过如下方式我们可以获取到当前用户的用户
        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(tokenHeader);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write(JSON.toJSONString(new ResponseResult(AdminCode.LOGIN_ERROR)));
            return;
        }
        super.doFilterInternal(request, response, chain);
    }
    /**
     * 这里从token中获取用户信息并新建一个token(默认)
     * @description
     * @author ClearLi
     * @date 2020/9/23 10:58
     * @param tokenHeader token
     * @return org.springframework.security.authentication.UsernamePasswordAuthenticationToken
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
        log.info(tokenHeader);
        String[] split = tokenHeader.split("\\s+");
       // String token = tokenHeader.replace(JwtUtil.TOKEN_PREFIX, "");
      // log.info(token);
       //从token中获取用户信息

        SrAdmin srAdmin =JwtUtil.getAdminByToken(split[1]);
        SrAdmin adminById = adminService.getAdminById(srAdmin.getId());
        //log.info(adminById.toString());
        if (adminById != null){
            return new UsernamePasswordAuthenticationToken(srAdmin.getId(), null,
                    Lists.newArrayList(new SimpleGrantedAuthority(adminById.getSrRoleList().get(0).getRoleName())));
        }
        return null;
    }
}
