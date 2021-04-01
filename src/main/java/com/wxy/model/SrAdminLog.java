package com.wxy.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * sr_admin_log
 * @author 
 */
@ApiModel(value="generate.SrAdminLogsr_admin_log 管理员日志表")
@Data
public class SrAdminLog implements Serializable {
    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="id")
    private Long id;

    /**
     * admin_id 管理员id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="admin_id 管理员id")
    private Long adminId;

    /**
     * 管理员名称
     */
    @ApiModelProperty(value="管理员名称")
    private String adminName;

    /**
     * 被操作的用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="被操作的用户id")
    private Long operationUserid;

    /**
     * 被操作的用户名称
     */
    @ApiModelProperty(value="被操作的用户名称")
    private String operationUsername;

    @ApiModelProperty(value="操作的结果")
    private String operationResult;
    /**
     * ip ip
     */
    @ApiModelProperty(value="ip ip")
    private String ip;

    /**
     * operation_model 操作的模块名
     */
    @ApiModelProperty(value="operation_model 操作的模块名")
    private String operationModel;

    /**
     * log_desc 传入的参数 等信息
     */
    @ApiModelProperty(value="log_desc 传入的参数 等信息")
    private String logDesc;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Long createTime;

    private static final long serialVersionUID = 1L;
}