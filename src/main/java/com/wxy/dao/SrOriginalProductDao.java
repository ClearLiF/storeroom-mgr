package com.wxy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxy.model.SrOriginalProduct;
import com.wxy.model.SrRole;
import com.wxy.model.request.SearchProductDTO;

import java.util.List;

/**
 * @author wxy
 */
public interface SrOriginalProductDao extends BaseMapper<SrOriginalProduct> {


    List<SrOriginalProduct> getProductList(String type, SearchProductDTO searchStore);
}