package com.wxy.model.response;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wxy.exception.ExceptionCast;
import com.wxy.model.SrRole;
import com.wxy.model.SrStorehouse;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * sr_admin
 * @author 
 */
@Data
public class SrAdminVO implements Serializable {
    /**
     * id 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * username 登录账号
     */
    private String username;



    private String userMail;

    /**
     * user_nickname 管理员昵称
     */
    private String userNickname;

    /**
     * user_avatar 用户头像
     */
    private String userAvatar;

    /**
     * last_login_time 上次登录时间
     */
    private Long  lastLoginTime;

    /**
     * 创建时间 创建时间
     */
    private Long createTime;

    /**
     * 更新时间 更新时间
     */
    private Long updateTime;

    private SrRole srRole;

    private SrStorehouse storehouseInfo;




}