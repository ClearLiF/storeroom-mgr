package com.wxy.model.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : wxj
 * @version : V1.0
 * @className : SearchProductDTO
 * @packageName : com.wxy.model.request
 * @description : 查询仓库列表类
 * @date : 2021-03-20 15:57
 **/
@Data
public class SearchStoreHouseDTO {

    private Integer pageNum;

    private Integer pageSize;
    /**
     * 0 全部 1 根据ip类型查询 2 根据qq
     */
    @ApiModelProperty(value=" 0 全部 1 根据地点查询 2根据名称查询")
    private String type;
    @ApiModelProperty(value=" 地点查询[当type=1 时有效]")
    private String site;
    @ApiModelProperty(value=" 名称查询[当type=2 时有效]")
    private String srName;
}
