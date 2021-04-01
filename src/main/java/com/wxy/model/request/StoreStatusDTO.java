package com.wxy.model.request;

import com.wxy.model.enums.SrStatusEnum;
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
public class StoreStatusDTO {

    /**
     *仓库id
     */
    private Long storeId;
    /**
     * 管理员id
     */
    private SrStatusEnum status;
}
