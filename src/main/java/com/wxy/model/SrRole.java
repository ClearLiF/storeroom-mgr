package com.wxy.model;

import java.io.Serializable;
import lombok.Data;

/**
 * sr_role
 * @author 
 */
@Data
public class SrRole implements Serializable {
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