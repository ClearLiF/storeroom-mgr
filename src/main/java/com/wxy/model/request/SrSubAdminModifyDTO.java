package com.wxy.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 修改下级管理员信息
 * @author 
 */
@Data
public class SrSubAdminModifyDTO implements Serializable {

    @ApiModelProperty(value="用户id")
    private Long id;

    /**
     * password 登录密码
     */
    @ApiModelProperty(value="用户密码")
    private String password;

    /**
     * user_nickname 管理员昵称
     */
    @ApiModelProperty(value="用户昵称")
    private String userNickname;

    /**
     * user_avatar 用户头像
     */
    @ApiModelProperty(value="用户头像")
    private String userAvatar;

    private static final long serialVersionUID = 1L;



}