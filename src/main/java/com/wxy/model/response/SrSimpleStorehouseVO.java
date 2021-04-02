package com.wxy.model.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : SrSimpleStorehouseVO
 * @packageName : com.wxy.model.response
 * @description : 一般类
 * @date : 2021-04-01 13:48
 **/
@Data
public class SrSimpleStorehouseVO {

    /**
     * 仓库id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     *
     * 仓库名称
     */
    private String srName;

}
