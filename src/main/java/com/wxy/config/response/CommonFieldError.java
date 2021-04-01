package com.wxy.config.response;

import lombok.Data;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : CommonFieldError
 * @packageName : com.yinghuo.framework.response
 * @description : 用于controller的参数检验 具体看
 * @date : 2020-10-17 16:44
 **/
@Data
public class CommonFieldError implements ResultCode{
    private Boolean success= false;
    private Integer code = 40040;
    private String message = "";
    @Override
    public boolean success() {
        return false;
    }

    @Override
    public int code() {
        return 0;
    }

    @Override
    public String message() {
        return null;
    }
}
