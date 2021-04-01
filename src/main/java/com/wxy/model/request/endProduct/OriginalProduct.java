package com.wxy.model.request.endProduct;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : OriginalProduct
 * @packageName : com.wxy.model.request.endProduct
 * @description : 一般类
 * @date : 2021-03-25 22:24
 **/
@Data
public class OriginalProduct {
    @ApiModelProperty(value="原材料id")
    private Long  productId;

    @ApiModelProperty(value="原材料所需数量")
    private Integer productNum;

}
