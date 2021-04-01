package com.wxy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wxy.model.SrAdmin;
import com.wxy.model.request.SearchAdminListDTO;

import java.util.List;

/**
 * @author  wxy
 */
public interface SrAdminDao extends BaseMapper<SrAdmin> {

    /**
     * 根据用户id查询用户的详细信息
     * @param userId 用户id
     * @return 用户详细信息
     */
    SrAdmin getUserInfoById(Long userId);

    /**
     * 根据用户名查询用户的详细信息
     *
     * @param userName 用户名
     * @return
     */
    SrAdmin selectByUserName(String userName);

    /**
     * 根据类型查询用户
     *
     * @param adminListDTO
     * @return
     */
    List<SrAdmin> getAdminListByType(SearchAdminListDTO adminListDTO,String type);

    /**
     * 获取所有仓库管理员
     *
     * @return
     */
    List<SrAdmin> getSubAdminList();

    SrAdmin getAdminInfo(Long userId);
}