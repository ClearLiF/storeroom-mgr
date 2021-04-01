package com.wxy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxy.model.SrInstorehouseLog;
import com.wxy.model.request.log.SearchInStorehouseLogDTO;
import com.wxy.model.response.log.StorehouseCountLogVO;

import java.util.List;

public interface SrInstorehouseLogDao extends BaseMapper<SrInstorehouseLog> {

    /**
     * 根据查询条件查询日志
     *
     * @param search 查询条件
     * @param type 类型
     * @param filter 1超级管理员 2仓库管理员
     * @param userId 管理员id 当filter = 2时有效
     * @return
     */
    List<SrInstorehouseLog> getPurchaseLogByType(SearchInStorehouseLogDTO search,
                                                 String type, String filter,Long userId);

    /**
     * 根据产品查询每月情况
     *
     * @param productId
     * @param userId
     * @return
     */
    List<StorehouseCountLogVO> getInStorehouseCountLogStoreRoomAdmin(Long productId, Long userId);

    List<StorehouseCountLogVO> getInStorehouseCountLog(Long productId);
}