package com.wxy.model.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wxy.model.SrRole;
import lombok.Data;

import java.io.Serializable;

/**
 * sr_admin
 * @author 
 */
@Data
public class SrSubAdminVO implements Serializable {
    /**
     * id 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * username 登录账号
     */
    private String username;
}