package com.wxy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxy.model.SrStorehouse;
import com.wxy.model.request.SearchStoreHouseDTO;

import java.util.List;

/**
 * @author wxy
 */
public interface SrStorehouseDao extends BaseMapper<SrStorehouse> {

    List<SrStorehouse> getStorehouseListByType(String type, SearchStoreHouseDTO searchStore);

    void reduceProduct(Long storehouseId, Integer size, long time);
}