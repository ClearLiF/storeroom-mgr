package com.wxy.model.request.endProduct;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : EndProductMapDTO
 * @packageName : com.wxy.model.request.endProduct
 * @description : 一般类
 * @date : 2021-04-01 19:20
 **/
@Data
public class EndProductMapDTO {

    /**
     *
     */
    @ApiModelProperty(value="原材料与原料的对应id")
    private Long productMapId;

    @ApiModelProperty(value="要更改的数量")
    private Integer productNum;
}
