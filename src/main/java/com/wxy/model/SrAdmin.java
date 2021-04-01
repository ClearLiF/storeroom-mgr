package com.wxy.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wxy.exception.ExceptionCast;
import com.wxy.model.enums.DeleteMarkEnum;
import com.wxy.model.response.AdminCode;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * sr_admin
 * @author 
 */
@Data
public class SrAdmin implements Serializable, UserDetails {
    /**
     * id 用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

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
     * last_login_time 上次登录时间
     */
    private Long  lastLoginTime;

    /**
     * 创建时间 创建时间
     */
    private Long createTime;

    /**
     * 删除标记
     */
    private DeleteMarkEnum deleteMark;
    /**
     * 更新时间 更新时间
     */
    private Long updateTime;

    @TableField(exist = false)
    private List<SrRole> srRoleList;
    public static SrAdmin fromTokenGetUserId(String  request){
        SrAdmin srAdmin = new SrAdmin();
        try {
            srAdmin = JSON.parseObject(request,SrAdmin.class);
        } catch (Exception e) {
            ExceptionCast.cast(AdminCode.TIME_OUT);
        }
        return srAdmin;
    }
    /**
     * 用户所有权限 默认权限为0 也就是没有权限
     */
    @TableField(exist = false)
    private List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
    private static final long serialVersionUID = 1L;
    @TableField(exist = false)
    private Integer role = 0;
    @TableField(exist = false)
    private SrStorehouse storehouseInfo;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorityList;
    }

    /**
     * 账户是否未过期
     */

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * // 账户是否未锁定
     */

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /**
     *  密码是否未过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     *    // 账户是否激活
     */

    @Override
    public boolean isEnabled() {
        return true;
    }
}