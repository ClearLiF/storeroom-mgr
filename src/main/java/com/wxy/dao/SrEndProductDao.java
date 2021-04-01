package com.wxy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxy.model.SrAdminRole;
import com.wxy.model.SrEndProduct;
import com.wxy.model.request.endProduct.SearchEndProductDTO;

import java.util.List;

/**
 * @author wxy
 */
public interface SrEndProductDao extends BaseMapper<SrEndProduct> {

    List<SrEndProduct> getEndProductByType(SearchEndProductDTO search, String type,int skip ,int size);

    int selectCountByType(SearchEndProductDTO search, String type);
}