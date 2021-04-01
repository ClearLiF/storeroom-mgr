package com.wxy.model.response;

import lombok.Data;

import java.io.Serializable;

/**
 * sr_role
 * @author 
 */
@Data
public class SrRoleVO implements Serializable {
    /**
     * id 权限id
     */
    private Integer id;

    /**
     * role_name 权限名称
     */
    private String roleName;

    /**
     * role_desc 说明内容
     */
    private String roleDesc;

    private static final long serialVersionUID = 1L;
}