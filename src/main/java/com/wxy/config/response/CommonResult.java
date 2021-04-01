package com.wxy.config.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : CommonResult
 * @packageName : com.yinghuo.framework.response
 * @description : //已被废弃
 * @date : 2020-10-16 16:55
 **/
@Data
@Deprecated
public class CommonResult {
    @ApiModelProperty(value = "参数map集合")
    Map<String, Object> data;
    @ApiModelProperty(value = "调用结果code")
    ResponseResult responseResult;
    public CommonResult(ResultCode resultCode, Map<String, Object> map) {
        responseResult=new ResponseResult(resultCode);
        this.data = map;
    }
}
