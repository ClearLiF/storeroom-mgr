package com.wxy.model.response;

import com.wxy.config.response.ResultCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * @author : wxy
 * @version : V1.0
 * @className : AdminCode
 * @packageName : com.yinghuo.framework.domain.management.admin
 * @description : 一般类
 * @date : 2020-09-23 13:40
 **/
@Getter
public enum ProductCode implements ResultCode {
    /**
     * 货物code
     */
    NO_SUCH_GOODS(false,10100,"没有该货物请和超级管理员联系"),
    NO_SPACE(false,10102,"仓库位置不够请选择其他仓库"),
    NO_GOODS(false,10103,"该仓库没有足够的货物转出"),
    WAREHOUSE_DECOMMISSIONED(false,10101,"抱歉，该仓库已经停用");

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

    ProductCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public String message() {
        return message;
    }
}
