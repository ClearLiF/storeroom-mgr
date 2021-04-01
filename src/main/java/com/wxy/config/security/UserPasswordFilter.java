package com.wxy.config.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wxy.config.response.ResponseResult;
import com.wxy.model.SrAdmin;
import com.wxy.model.response.AdminCode;
import com.wxy.model.response.SrAdminLoginVO;
import com.wxy.utils.GetRequestJsonUtils;
import com.wxy.utils.JwtUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : JWTAuthenticationFilter
 * @packageName : com.yinghuo.management.handler
 * @description : 登录验证类 2
 * @date : 2020-09-22 22:44
 **/
@Slf4j
public class UserPasswordFilter extends UsernamePasswordAuthenticationFilter {

   private AuthenticationManager authenticationManager;

    public UserPasswordFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/auth/login");
    }

    /**
     * // 接收并解析用户登陆信息，为已验证的用户返回一个已填充的身份验证令牌，表示成功的身份验证，
     * // 如果身份验证过程失败，就抛出一个AuthenticationException
     *
     * @param request req
     * @param response res
     * @return
     */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) {

        SrAdmin httpUser = getHttpUser(request, response);
        if (httpUser == null) {
            log.warn("非登录请求");
            return null;
        } else {
            Authentication authenticate = null;
            try {
                // 方法将 request 中的 username 和 password 生成 UsernamePasswordAuthenticationToken 对象，用于 AuthenticationManager 的验证
                //获取用户名密码信息  用于检测用户信息
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(httpUser.getUsername(),
                        httpUser.getPassword());
                setDetails(request, authentication);
                authenticate =this.getAuthenticationManager().authenticate(authentication) ;
            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().write(JSON.toJSONString(new ResponseResult(AdminCode.LOGIN_ERROR)));
            }
            if (authenticate!=null){
                SrAdmin jwtUser = (SrAdmin) authenticate.getPrincipal();
                if (jwtUser.getRole()==-1){
                    log.info(httpUser.getUsername()+"被封禁");
                    response.getWriter().write(JSON.toJSONString(new ResponseResult(AdminCode.BIN_OUT)));
                    return null;
                }
            }
            return authenticate;
        }

    }

    /**
     * 成功验证后调用的方法
     * 如果验证成功，就生成token并返回
     *
     * @param request    req
     * @param response   res
     * @param chain      cha
     * @param authResult auth
     * @return void
     * @description
     * @author ClearLi
     * @date 2020/9/23 11:00
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {

        SrAdmin jwtUser = (SrAdmin) authResult.getPrincipal();
        log.info("jwtUser:" + jwtUser.toString());
       // List<GrantedAuthority> grantedAuthorityList = jwtUser.getGrantedAuthorityList();
//        String authority = "";
//        if (CollectionUtil.isNotEmpty(grantedAuthorityList)) {
//            authority = grantedAuthorityList.get(0).getAuthority();
//        }
        String token = JwtUtil.createToken(jwtUser, false);
        // 返回创建成功的token
        response.setHeader("token", JwtUtil.TOKEN_PREFIX + token);
        response.setHeader("Access-Control-Expose-Headers", "token");
        SrAdminLoginVO srAdminLoginVO = new SrAdminLoginVO();
        srAdminLoginVO.setToken(JwtUtil.TOKEN_PREFIX + token);
        response.getWriter().write(JSON.toJSONString(srAdminLoginVO));
    }

    /**
     * 这是验证失败时候调用的方法
     *
     * @param request  req
     * @param response res
     * @param failed   fail
     * @return void
     * @description
     * @author ClearLi
     * @date 2020/9/23 10:59
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.getWriter().write(JSON.toJSONString(new ResponseResult(AdminCode.LOGIN_ERROR)));
    }

    @SneakyThrows
    private SrAdmin getHttpUser(HttpServletRequest request,
                                HttpServletResponse response) {
        SrAdmin loginUser = new SrAdmin();
        //解析json
        String requestPostStr = GetRequestJsonUtils.getRequestPostStr(request);
        JSONObject jsonObject = JSONObject.parseObject(requestPostStr);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
//        String username2 = request.getParameter("username");
//        String password3 = request.getParameter("password");
        if (username == null) {
            try {
                loginUser = new ObjectMapper().readValue(request.getInputStream(), SrAdmin.class);
                return loginUser;
            } catch (IOException e) {
                response.getWriter().write(JSON.toJSONString(new ResponseResult(AdminCode.NULL_LOGIN)));
                return null;
            }
        }
        loginUser.setUsername(username);
        loginUser.setPassword(password);
        return loginUser;
    }

}
