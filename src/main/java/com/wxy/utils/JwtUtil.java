package com.wxy.utils;

import com.google.common.collect.Maps;
import com.wxy.exception.CustomException;
import com.wxy.exception.ExceptionCast;
import com.wxy.model.AdminRole;
import com.wxy.model.SrAdmin;
import com.wxy.model.response.AdminCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt工具包
 *
 * @author : wxy
 * @version : V1.0
 * @className : JavaJWTUtil
 * @packageName : com.yinghuo.management.utils
 * @description : 一般类
 * @date : 2020-09-22 21:38
 **/
@Slf4j
public class JwtUtil {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    private static final String SECRET = "wxy#adminjwt";
    private static final String ISS = "wxyadmin";

    /**
     * 过期时间是3600秒，既是1个小时
     */
    private static final long EXPIRATION = 3600L;
    /**
     * 选择了记住我之后的过期时间为7天(暂时没用)
     */
    private static final long EXPIRATION_REMEMBER = 604800L;
    private static final String ROLE_CLAIMS = "rol";
    private static final String ROLE_CLAIMS_ID = "rolId";
    private static final String SC_ID = "school";
    private static final String USER_ID = "userId";

    /**
     * 创建token
     *
     * @param srAdmin      登录用户
     * @param isRememberMe 是否被记住
     * @return java.lang.String
     * @description
     * @author wxy
     * @date 2020/9/23 16:51
     */
    public static String createToken(SrAdmin srAdmin, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRATION_REMEMBER : EXPIRATION;
        HashMap<String, Object> map = new HashMap<>(1);

//        Optional<GrantedAuthority> first = yhAdmin.getGrantedAuthorityList().stream().findFirst();
//        String authority = "";
//        if (first.isPresent()) {
//            authority = first.get().getAuthority();
//        }
//        map.put(ROLE_CLAIMS, authority);
//        map.put(SC_ID, yhAdmin.getSchoolId());
        map.put(USER_ID, srAdmin.getId());
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .setClaims(map)
                .setIssuer(ISS)
                //.setSubject(yhAdmin.getId())
                .setIssuedAt(new Date())
                //过期时间
               // .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .compact();
    }

    /**
     * 从token中获取用户名
     *
     * @param token 令牌
     * @return java.lang.String
     * @description
     * @author wxy
     * @date 2020/9/23 18:11
     */
    public static String getUsername(String token) {
        return getTokenBody(token).getSubject();
    }

    public static String getRoleClaims(String token) {
        return String.valueOf(getTokenBody(token).get(ROLE_CLAIMS));
    }

    /**
     * 判断当前令牌是否过期
     *
     * @param token 令牌
     * @return boolean
     * @description
     * @author wxy
     * @date 2020/9/23 15:33
     */
    public static boolean isExpiration(String token) {
        boolean status;

        try {
            status = getTokenBody(token).getExpiration().getTime() - System.currentTimeMillis() > 0;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return status;
    }

    public static SrAdmin getAdmin(HttpServletRequest request) {
        SrAdmin yhAdmin = new SrAdmin();
        String tokenHeader = request.getHeader(TOKEN_HEADER);
        String token = tokenHeader.replace(JwtUtil.TOKEN_PREFIX, "");
        Claims tokenBody = getTokenBody(token);
      //  yhAdmin.setUsername(tokenBody.getSubject());
//        Integer id = AdminRole.getRoleByName(String.valueOf(tokenBody.get(ROLE_CLAIMS))).getId();
//        if (id == null || id == 0) {
//            throw new CustomException(AdminCode.Failure);
//        }
//        yhAdmin.setRole(id);
        // yhAdmin.setSchoolId(String.valueOf(tokenBody.get(SC_ID)));
        yhAdmin.setId((Long) tokenBody.get(USER_ID));
        return yhAdmin;
    }

    public static long getAdminPrincipal() {
        return (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Deprecated
    public static SrAdmin getAdmin() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        if (sra == null) {
            throw new CustomException(AdminCode.LOG_FAILURE);
        }
        return getAdmin(sra.getRequest());
    }

    private static Claims getTokenBody(String token) {
        log.info(token);
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 解析token
     *
     * @param token token
     * @return
     */
    public static SrAdmin getAdminByToken(String token) {
        //如果过期
//        if (isExpiration(token)) {
//            ExceptionCast.cast(AdminCode.TIME_OUT);
//        }
        Claims tokenBody = getTokenBody(token);
        SrAdmin srAdmin = new SrAdmin();
        srAdmin.setId((Long) tokenBody.get(USER_ID));
        return srAdmin;
    }
}
