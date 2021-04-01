package com.wxy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxy.model.SrRole;
import com.wxy.model.SrTransferLog;
import com.wxy.model.request.log.SearchTransferLogDTO;
import com.wxy.model.response.log.StorehouseCountLogVO;

import java.util.List;

/**
 * @author wxy
 */
public interface SrTransferLogDao extends BaseMapper<SrTransferLog> {

    /**
     * 仓库管理员查看日志
     *
     * @param search 查询条件
     * @param type 类型
     * @param userId 管理员id
     * @return
     */
    List<SrTransferLog> getTransferLogByTypeStoreRoomAdmin(SearchTransferLogDTO search, String type, Long userId);

    /**
     * 超级管理员查看日志
     *
     * @param search 查询条件
     * @param type 类型
     * @return
     */
    List<SrTransferLog> getTransferLogByType(SearchTransferLogDTO search, String type);

    List<StorehouseCountLogVO> getTransferStorehouseCountLogStoreRoomAdmin(Long productId, Long userId);

    List<StorehouseCountLogVO> getTransferStorehouseCountLog(Long productId);
}