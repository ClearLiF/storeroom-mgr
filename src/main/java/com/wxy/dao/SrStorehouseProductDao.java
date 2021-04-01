package com.wxy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxy.model.SrStorehouseProduct;
import com.wxy.model.request.SearchStoreProductDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wxy
 */
public interface SrStorehouseProductDao extends BaseMapper<SrStorehouseProduct> {

    /**
     * 仓库管理员查看存货信息
     *
     * @param search 查询条件
     * @param type 类型
     * @param userId 仓库管理员id
     * @return
     */
    List<SrStorehouseProduct> getStoreProductByTypeStoreAdmin(@Param("search") SearchStoreProductDTO search,
                                                              @Param("type") String type,
                                                              @Param("userId") Long userId);

    /**
     * 超级管理员查看存货信息
     *
     * @param search 查询条件
     * @param type 类型
     * @return
     */
    List<SrStorehouseProduct> getStoreProductByTypeSuperAdmin(SearchStoreProductDTO search, String type);

    /**
     * 减少货物
     *
     * @param productId 货物id
     * @param productNum 货物数量
     * @param storehouseId 仓库id
     * @param time 时间
     */
    int reduceProduct(Long productId, Integer productNum, Long storehouseId, long time);
}