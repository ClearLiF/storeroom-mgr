package com.wxy.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : RefundDTO
 * @packageName : com.wxy.model.request
 * @description : 退货
 * @date : 2021-03-25 19:23
 **/
@Data
public class RefundDTO {

    /**
     * 仓库id
     */
    @ApiModelProperty(value = " 要退货的仓库id")
    private Long storehouseId;

    /**
     * 产品id
     */
    @ApiModelProperty(value = "产品id")
    private Long productId;

    /**
     * 退货数量
     */
    @ApiModelProperty(value = "退货数量")
    private Integer productNum;

    /**
     * 退货原因
     */
    @ApiModelProperty(value = "退货原因")
    private String refundReason;

}
