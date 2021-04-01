package com.wxy.model.request.endProduct;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : EndProductProduceDTO
 * @packageName : com.wxy.model.request.endProduct
 * @description : 一般类
 * @date : 2021-03-26 11:57
 **/
@Data
public class EndProductProduceDTO {
    /**
     * 成品id
     *
     */
    @ApiModelProperty(value="成品id")
    private Long endProductId;

    /**
     * 成品生产数量
     */
    @ApiModelProperty(value="成品生产数量")
    private Integer endProductNum;

    @ApiModelProperty(value="仓库id")
    private Long storehouseId;


}
