package com.wxy.model;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * sr_admin_role
 * @author 
 */
@Data
public class SrAdminRole implements Serializable {
    /**
     * admin_id 管理员id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long adminId;

    /**
     * role_id 权限id
     */
    private Integer roleId;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;

    private static final long serialVersionUID = 1L;
}