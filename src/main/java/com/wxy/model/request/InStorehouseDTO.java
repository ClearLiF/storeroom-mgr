package com.wxy.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : wxy
 * @version : V1.0
 * @className : InStorehouseDTO
 * @packageName : com.wxy.model.request
 * @description : 入库请求
 * @date : 2021-03-24 16:59
 **/
@Data
public class InStorehouseDTO {

    /**
     * 仓库id
     */
    @ApiModelProperty(value="仓库id")
    private Long storehouseId;

    /**
     * 产品id
     */
    @ApiModelProperty(value="产品id")
    private Long productId;

    /**
     * 进库数量
     */
    @ApiModelProperty(value="进库数量")
    private Integer productNum;
}
