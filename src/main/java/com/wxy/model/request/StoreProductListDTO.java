package com.wxy.model.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wxy.model.SrOriginalProduct;
import com.wxy.model.SrStorehouse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : wxy
 * @version : V1.0
 * @className : StoreProductListDTO
 * @packageName : com.wxy.model.request
 * @description : 库存管理时 返回的库存信息
 * @date : 2021-03-25 13:39
 **/
@Data
public class StoreProductListDTO {
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
    @ApiModelProperty(value="原材料信息")
    private SrOriginalProduct product;

    /**
     * 厂房信息
     */
    @ApiModelProperty(value="厂房信息")
    private SrStorehouse storehouse;
}
