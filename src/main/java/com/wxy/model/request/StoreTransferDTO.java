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
public class StoreTransferDTO {

    /**
     * 仓库id
     */
    @ApiModelProperty(value="从哪个仓库转出的仓库id")
    private Long formStorehouseId;

    /**
     * 仓库id
     */
    @ApiModelProperty(value="到哪个仓库转出的仓库id")
    private Long toStorehouseId;

    /**
     * 产品id
     */
    @ApiModelProperty(value="产品id")
    private Long productId;

    /**
     * 转入数量
     */
    @ApiModelProperty(value="转入数量")
    private Integer productNum;
}
