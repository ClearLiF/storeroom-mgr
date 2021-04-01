package com.wxy.model.request;

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
public class SearchAdminListDTO {

    private Integer pageNum;

    private Integer pageSize;
    /**
     * 0 全部 1 根据管理员名称查询
     */
    @ApiModelProperty(value=" 0 全部 1 根据管理员名称查询")
    private String type;
    @ApiModelProperty(value=" 商品名称[当type=1 时有效]")
    private String adminName;

}
