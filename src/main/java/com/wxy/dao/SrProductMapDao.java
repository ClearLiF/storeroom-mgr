package com.wxy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxy.model.SrProductMap;

import java.util.List;

public interface SrProductMapDao extends BaseMapper<SrProductMap> {

    List<SrProductMap> getEndProductProduce(Long endProductId);
}