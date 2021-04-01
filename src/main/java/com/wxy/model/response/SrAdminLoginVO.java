package com.wxy.model.response;

import lombok.Data;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : SrAdminLoginVo
 * @packageName : com.wxy.model.response
 * @description : 一般类
 * @date : 2021-03-28 16:42
 **/
@Data
public class SrAdminLoginVO {

    /**
     * 操作是否成功
     */
   private boolean success = true;

    /**
     * 操作代码
     */
    private int code = 200;

    /**
     * 提示信息
     */
    private String message = "登录成功";

    private String token;
}
