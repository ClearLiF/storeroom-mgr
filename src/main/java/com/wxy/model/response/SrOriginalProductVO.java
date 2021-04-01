package com.wxy.model.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wxy.model.SrAdmin;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * sr_original_product
 * @author 
 */
@Data
public class SrOriginalProductVO implements Serializable {
    /**
     * id 原材料id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="id 原材料id")
    private Long id;

    /**
     * 原材材料名称
     */
    @ApiModelProperty(value="原材材料名称")
    private String productName;

    /**
     * 物料简介
     */
    @ApiModelProperty(value="物料简介")
    private String productDesc;

    /**
     * 物料图片
     */
    @ApiModelProperty(value="物料图片")
    private String productImage;

    /**
     * 物料来源
     */
    @ApiModelProperty(value="物料来源")
    private String productSource;

    /**
     * 单位物料所占大小(单位 m2)
     */
    @ApiModelProperty(value="单位物料所占大小(单位 m2)")
    private Integer productSize;

    /**
     * 物料价格(单位分)
     */
    @ApiModelProperty(value="物料价格(单位分)")
    private Integer productPrice;

    /**
     * 物料创建人id
     */

    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="物料创建人id")
    private Long userId;



    @ApiModelProperty(value="物料创建人信息")
    private SrAdmin srAdmin;

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

    private static final long serialVersionUID = 1L;
}