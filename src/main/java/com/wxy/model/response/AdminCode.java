package com.wxy.model.response;

import com.wxy.config.response.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : AdminCode
 * @packageName : com.yinghuo.framework.domain.management.admin
 * @description : 一般类
 * @date : 2020-09-23 13:40
 **/
@Getter
public enum AdminCode implements ResultCode {
    /**
     * 当登陆时没有用户名和密码
     */
    NULL_LOGIN(false,10100,"用户登录信息不能为空"),
    FAIL_LOGIN(false,10400,"登陆失败"),
    QPS_ERROR(false,10600,"qps超过限制"),
    TIME_OUT(false,10500,"登陆超时"),
    BIN_OUT(false,10600,"已被封禁"),
    SUCCESS_LOGIN(true,10000,"登陆成功"),
    SUCCESS(true,10000,"调用成功"),
    Failure(false,11111,"调用失败"),
    AUTH_FAILURE(false,500,"权限出错"),
    LOG_FAILURE(false,501,"日志写入出错"),
    USER_REPEAT(false,502,"用户名重复"),
    LOGIN_ERROR(false,88888,"登录参数出错");
    /**
     * 操作结果
     */
    @ApiModelProperty(value = "操作是否成功", example = "true", required = true)
    boolean success;

    /**
     * 操作代码
     */

    @ApiModelProperty(value = "操作代码", example = "22001", required = true)
    int code;
    /**
     * 提示信息
     */
    @ApiModelProperty(value = "操作提示", example = "操作过于频繁！", required = true)
    String message;
    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    AdminCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public String message() {
        return message;
    }
}
