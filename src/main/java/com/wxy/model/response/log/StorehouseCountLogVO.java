package com.wxy.model.response.log;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : StorehouseCountLogVO
 * @packageName : com.wxy.model.response.log
 * @description : 一般类
 * @date : 2021-03-26 14:47
 **/
@Data
public class StorehouseCountLogVO {

    /**
     * 入库总数量
     */
    @ApiModelProperty(value="总数量")
    private Integer count;


    @ApiModelProperty(value="年份")
    private Integer month;
}
