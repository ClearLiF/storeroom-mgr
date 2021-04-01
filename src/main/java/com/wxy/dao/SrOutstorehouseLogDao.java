package com.wxy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxy.model.SrOutstorehouseLog;
import com.wxy.model.request.log.SearchOutLogDTO;
import com.wxy.model.response.log.StorehouseCountLogVO;

import java.util.List;

public interface SrOutstorehouseLogDao extends BaseMapper<SrOutstorehouseLog> {

    List<SrOutstorehouseLog> getOutProductLogByTypeStoreRoomAdmin(SearchOutLogDTO search, String type, Long userId);

    List<SrOutstorehouseLog> getOutLogByType(SearchOutLogDTO search, String type);

    List<StorehouseCountLogVO> getOutStorehouseCountLogStoreRoomAdmin(Long productId, Long userId);


    List<StorehouseCountLogVO> getOutStorehouseCountLog(Long productId);
}