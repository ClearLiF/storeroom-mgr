package com.wxy.model.response.endProduct;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wxy.model.SrProductMap;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * sr_end_product
 * @author 
 */
@Data
public class EndProductVO implements Serializable {
    /**
     * 成品id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="成品id")
    private Long id;

    /**
     * 成品名称
     */
    @ApiModelProperty(value="成品名称")
    private String productName;

    /**
     * 成品图片
     */
    @ApiModelProperty(value="成品图片")
    private String productImage;

    /**
     * 成品成本(单位分)
     */
    @ApiModelProperty(value="成品成本(单位分)")
    private Integer productPrice;

    /**
     * 成品创建人
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="成品创建人")
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
    @ApiModelProperty(value="更新时间")
    private List<SrProductMap> srProductMapList;

    private static final long serialVersionUID = 1L;
}