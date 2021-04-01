package com.wxy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxy.model.SrOriginalProduct;
import com.wxy.model.SrRefundLog;
import com.wxy.model.request.log.SearchRefundLogDTO;
import com.wxy.model.response.log.StorehouseCountLogVO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author wxy
 */
public interface SrRefundLogDao extends BaseMapper<SrRefundLog> {

    List<SrRefundLog> getRefundLogByTypeStoreRoomAdmin(SearchRefundLogDTO search, String type, Long userId);

    List<SrRefundLog> getRefundLogByType(SearchRefundLogDTO search, String type);

    List<StorehouseCountLogVO> getRefundStorehouseCountLogStoreRoomAdmin(Long productId, Long userId);

    List<StorehouseCountLogVO> getRefundStorehouseCountLog(Long productId);
}