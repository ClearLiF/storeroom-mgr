package com.wxy.config.response;

import lombok.Getter;
import lombok.ToString;

/**
 * 2020.9.19
 *
 * @author wxy
 */
@ToString
@Getter
public enum CommonCode implements ResultCode {
    //枚举类型
    INVALID_PARAM(false, 10003, "非法参数"),
    SUCCESS(true, 200, "操作成功！"),
    PERMISSION_DENIED(false, 10001, "没有权限！"),
    SERVER_ERROR(false, 500, "抱歉，系统繁忙，请稍后重试！"),
    FAIL(false, 500, "操作失败！");
    /**
     * 操作是否成功
     */
    boolean success;
    /**
     * 操作代码
     */
    int code;
    /**
     * 提示信息
     */
    String message;

    private CommonCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }


}
