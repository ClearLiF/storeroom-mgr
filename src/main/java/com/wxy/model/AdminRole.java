package com.wxy.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : AdminRole
 * @packageName : com.yinghuo.framework.domain.management.admin.request
 * @description : 角色
 * @date : 2020-09-24 14:03
 **/
@Getter
public enum AdminRole {
    /*
     *系统管理员
     */
    SUP_ADMIN(1, "ROLE_SUP_ADMIN"),
    /*
     *仓库管理员
     */
    STOREROOM_ADMIN(2, "ROLE_STOREROOM_ADMIN");


    private Integer id;
    private String role;

    AdminRole(Integer id, String role) {
        this.id = id;
        this.role = role;
    }
    public static AdminRole getRoleById(Integer id){
       Optional<AdminRole> first = Arrays.stream(values()).filter(rol -> rol.id.equals(id)).findFirst();
        return first.orElse(null);
    }
    public static AdminRole getRoleByName(String  role){
        Optional<AdminRole> first = Arrays.stream(values()).filter(rol -> rol.role.equals(role)).findFirst();
        return first.orElse(null);
    }
}
