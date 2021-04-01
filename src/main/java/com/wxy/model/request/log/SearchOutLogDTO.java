package com.wxy.model.request.log;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : wxj
 * @version : V1.0
 * @className : SearchProductDTO
 * @packageName : com.wxy.model.request
 * @description : 一般类
 * @date : 2021-03-20 15:57
 **/
@Data
public class SearchOutLogDTO {

    private Integer pageNum;

    private Integer pageSize;
    /**
     * 0 全部 1 根据管理员名称查询
     */
    @ApiModelProperty(value=" 0 全部 1.根据成品名称查询 ")
    private String type;

    @ApiModelProperty(value=" 根据成品名称查询 ")
    private String productName;

}
