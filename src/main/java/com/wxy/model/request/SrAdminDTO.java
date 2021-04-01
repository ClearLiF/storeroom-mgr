package com.wxy.model.request;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * sr_admin
 * @author 
 */
@Data
public class SrAdminDTO implements Serializable {


    /**
     * username 登录账号
     */
    private String username;

    /**
     * password 登录密码
     */
    private String password;

    /**
     * user_nickname 管理员昵称
     */
    private String userNickname;

    /**
     * user_avatar 用户头像
     */
    private String userAvatar;

    /**
     * 仓库id
     */
    private Long storeHoseId;





    private static final long serialVersionUID = 1L;
    /**
     * 权限角色
     */
    @ApiParam(name = "权限角色[1 超级管理员 2仓库管理员]",value = "role",example = "1",required = true)
    private Integer role = 0;



}