package com.wxy.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * sr_product_map
 * @author 
 */
@ApiModel(value="generate.SrProductMap成品与原材料的对应关系（一对多关系） ")
@Data
public class SrProductMap implements Serializable {
    /**
     * id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="id")
    private Long id;

    /**
     * 原材料id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="原材料id")
    private Long originalProductId;

    /**
     * 成品id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="成品id")
    private Long endProductId;

    /**
     * 原材料消耗数量
     */
    @ApiModelProperty(value="原材料消耗数量")
    private Integer productNum;

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
    @ApiModelProperty(value="原材料详情")
    private SrOriginalProduct originalProductInfo;

    private static final long serialVersionUID = 1L;
}