package com.wxy.model.request;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.io.Serializable;

/**
 * sr_admin
 * @author 
 */
@Data
public class SrAdminModifyDTO implements Serializable {


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
     * 用户邮箱
     */
    private String userMail;

    private static final long serialVersionUID = 1L;



}