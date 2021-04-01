package com.wxy.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : wxj
 * @version : V1.0
 * @className : SearchProductDTO
 * @packageName : com.wxy.model.request
 * @description : 库存查询
 * @date : 2021-03-20 15:57
 **/
@Data
public class SearchStoreProductDTO {

    private Integer pageNum;

    private Integer pageSize;
    /**
     * 0 全部 1 根据ip类型查询 2 根据qq
     */
    @ApiModelProperty(value=" 0 全部 1 根据商品名称查询 2根据库房id查询")
    private String type;
    @ApiModelProperty(value=" 原材料名称[当type=1 时有效]")
    private String productName;

    @ApiModelProperty(value=" 库房id")
    private Long storeProductId;

}
