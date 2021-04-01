package com.wxy.model.request;

import lombok.Data;

/**
 * @author : wxy
 * @version : V1.0
 * @className : StoreAdminDTO
 * @packageName : com.wxy.model.request
 * @description : 一般类
 * @date : 2021-03-24 15:44
 **/
@Data
public class StoreAdminDTO {

    /**
     *仓库id
     */
    private Long storeId;
    /**
     * 管理员id
     */
    private Long adminId;
}
