package com.wxy.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * sr_storehouse_product
 * @author 
 */
@ApiModel(value="generate.SrStorehouseProduct库房与原材料(库存表单) ")
@Data
public class SrStorehouseProduct implements Serializable {
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
     * 库房id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="库房id")
    private Long storehouseId;

    /**
     * 数量
     */
    @ApiModelProperty(value="数量")
    private Integer productNum;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String srDesc;

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

    /**
     * 原材料信息
     */
    @TableField(exist = false)
    private SrOriginalProduct product;

    /**
     * 厂房信息
     */
    @TableField(exist = false)
    private SrStorehouse storehouse;
    private static final long serialVersionUID = 1L;
}