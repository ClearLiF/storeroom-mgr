package com.wxy.config.security;

import com.wxy.config.CORSConfig;
import com.wxy.service.AdminService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : SecurityConfig
 * @packageName : com.yinghuo.management.config
 * @description : Security配置
 * //必须有全部的权限才可以访问
 * //@PreAuthorize("hasRole('ROLE_admin') and hasAnyRole('ROLE_user')")
 * //至少有一个即可访问
 * // @PreAuthorize("hasRole('ROLE_admin') or hasAnyRole('ROLE_user')")
 * @date : 2020-09-19 21:32
 **/
@Component
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Resource
    private AdminService adminService;

    /**
     * @param auth 权限
     * @return void
     * @description 用户认证信息
     * @author ClearLi
     * @date 2020/9/19 21:34
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 设置用户账号信息和权限
        auth.userDetailsService(adminService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence charSequence) {
                return bCryptPasswordEncoder().encode(String.valueOf(charSequence));
            }

            @Override
            public boolean matches(CharSequence charSequence, String s) {
                return bCryptPasswordEncoder().matches(String.valueOf(charSequence), s);
            }
        });
    }

    /**
     * 加密密码
     *
     * @return org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
     * @description
     * @author ClearLi
     * @date 2020/9/23 0:32
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * @param http http请求
     * @return void
     * @description 配置HttpSecurity 拦截资源 formLogin模式
     * @author ClearLi
     * @date 2020/9/19 21:35
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {


        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry
                = http.authorizeRequests();
        //读取数据库权限列表
//        List<YhPermission> yhPermissions = adminService.getAllAuthority();
//        yhPermissions.forEach(yhPermission -> expressionInterceptUrlRegistry
//                .antMatchers(yhPermission.getUrl())
//                .hasAnyAuthority(yhPermission.getPermissionName()));
        expressionInterceptUrlRegistry.antMatchers("/auth/login", "/management/auth/login"
                , "/auth/register", "/v2/api-docs",
                "/swagger-resources/configuration/ui",
                "/swagger-resources","/uploadpic",
                "/swagger-resources/configuration/security",
                "/swagger-ui.html","api-docs","/adminInfoInit",
                "/webjars/**","/qq/**","/doc.html","/admin/loginByOpenId/**","/qq/oauth","/qq/qq/callback")
                .permitAll()
                .antMatchers("/**").fullyAuthenticated().and()
                //账号密码验证
                .addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                //token验证
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))

                // 不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().cors().and().csrf().disable();
        //.and().formLogin().and().csrf().disable();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    /*跨域原*/
    private CorsConfigurationSource CorsConfigurationSource() {
        CorsConfigurationSource source =   new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");    //同源配置，*表示任何请求都视为同源，若需指定ip和端口可以改为如“localhost：8080”，多个以“，”分隔；
        corsConfiguration.addAllowedHeader("*");//header，允许哪些header，本案中使用的是token，此处可将*替换为token；
        corsConfiguration.addAllowedMethod("*");    //允许的请求方法，PSOT、GET等
        ((UrlBasedCorsConfigurationSource) source).registerCorsConfiguration("/**",corsConfiguration); //配置允许跨域访问的url
        return source;
    }
    @Bean
    UserPasswordFilter customAuthenticationFilter() throws Exception {
        UserPasswordFilter filter = new UserPasswordFilter(authenticationManager());

        //filter.setFilterProcessesUrl("/login/self");
        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManager());
        return filter;
    }
}
