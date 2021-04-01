package com.wxy.exception;

import com.wxy.config.response.CommonFieldError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

/**
 *
 * @author : CLEAR Li
 * @version : V1.0
 * @className : ControllerFieldError
 * @packageName : com.yinghuo.management.utils
 * @description : 对于传入参数的处理 检测是否有null的情况 或者例如密码长度传入非法等问题
 * @date : 2020-09-23 23:04
 **/
@Slf4j
public class ControllerFieldError {
    public static CommonFieldError getError(BindingResult result){
        String collect = result.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        CommonFieldError responseResult = new CommonFieldError();
        responseResult.setMessage(collect);
        return responseResult;
    }

}

