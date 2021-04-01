package com.wxy.handler;

import java.lang.annotation.*;

/**
 * @description  管理员切面的注解
 * @author clear
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SrAdminLogAspect {
    /**
     * 用户id的名称
     */
    String userId() default "userId";
    /**
     * 管理员操作的名称
     */
    SrAdminAspect.AdminAction operateName() ;
}
