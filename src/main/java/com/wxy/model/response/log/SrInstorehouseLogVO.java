package com.wxy.model.response.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wxy.model.SrAdmin;
import com.wxy.model.SrOriginalProduct;
import com.wxy.model.SrStorehouse;
import com.wxy.model.enums.ProductStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * sr_instorehouse_log
 * @author 
 */
@Data
public class SrInstorehouseLogVO implements Serializable {
    /**
     * 入库id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="入库id")
    private Long id;

    /**
     * 入库产品id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="入库产品id")
    private Long productId;


    /**
     * 仓库id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="仓库id")
    private Long storehouseId;
    /**
     * 入库数量
     */
    @ApiModelProperty(value="入库数量")
    private Integer productNum;

    /**
     * 入库实付金额
     */
    @ApiModelProperty(value="入库实付金额")
    private Integer payNum;

    /**
     * 入库状态(0已入库 1未入库 2退货取消入库)
     */
    @ApiModelProperty(value="入库状态(0已入库 1未入库 2退货取消入库)")
    private ProductStatusEnum productStatus;

    /**
     * 入库操作员
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="入库操作员")
    private Long userId;

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


    @TableField(exist = false)
    @ApiModelProperty(value="管理员详情")
    private SrAdmin adminInfo;

    @TableField(exist = false)
    @ApiModelProperty(value="仓库详情")
    private SrStorehouse storehouseInfo;

    @TableField(exist = false)
    @ApiModelProperty(value="原材料详情")
    private SrOriginalProduct productInfo;

    private static final long serialVersionUID = 1L;
}