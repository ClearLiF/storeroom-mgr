package com.wxy.model.request.endProduct;

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
public class EndProductDTO implements Serializable {


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


    @ApiModelProperty(value="所需原材料列表")
    private List<OriginalProduct> originalProductList;
    private static final long serialVersionUID = 1L;
}