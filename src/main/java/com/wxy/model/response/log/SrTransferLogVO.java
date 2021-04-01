package com.wxy.model.response.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wxy.model.SrAdmin;
import com.wxy.model.SrOriginalProduct;
import com.wxy.model.SrStorehouse;
import com.wxy.model.enums.TransferStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * sr_transfer_log
 * @author 
 */
@Data
public class SrTransferLogVO implements Serializable {
    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="id")
    private Long id;

    /**
     * 原材料id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="原材料id")
    private Long productId;

    /**
     * 调拨数量
     */
    @ApiModelProperty(value="调拨数量")
    private Integer productNum;

    /**
     * 调拨状态(0已调拨 1未调拨 2取消调拨)
     */
    @ApiModelProperty(value="调拨状态(0已调拨 1未调拨 2取消调拨)")
    private TransferStatusEnum transferStatus;

    /**
     * 操作员
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="操作员")
    private Long userId;

    /**
     * 原仓库
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="原仓库")
    private Long fromStorehouseId;

    /**
     * 调拨到的仓库id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="调拨到的仓库id")
    private Long toStorehouseId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Long createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Long updateTime;

    @ApiModelProperty(value="调拨到的仓库信息")
    private SrStorehouse toStorehouse;

    @ApiModelProperty(value="原仓库信息")
    private SrStorehouse fromStorehouse;

    @ApiModelProperty(value="管理员信息")
    private SrAdmin adminInfo;

    @ApiModelProperty(value="原材料信息")
    private SrOriginalProduct productInfo;

    private static final long serialVersionUID = 1L;
}