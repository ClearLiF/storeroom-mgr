package com.wxy.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.wxy.config.response.QueryResponseResult;
import com.wxy.dao.SrInstorehouseLogDao;
import com.wxy.dao.SrOutstorehouseLogDao;
import com.wxy.dao.SrRefundLogDao;
import com.wxy.dao.SrTransferLogDao;
import com.wxy.model.*;
import com.wxy.model.request.log.SearchInStorehouseLogDTO;
import com.wxy.model.request.log.SearchOutLogDTO;
import com.wxy.model.request.log.SearchRefundLogDTO;
import com.wxy.model.request.log.SearchTransferLogDTO;
import com.wxy.model.response.log.*;
import com.wxy.utils.StreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : StorehouseLogService
 * @packageName : com.wxy.service
 * @description : 一般类
 * @date : 2021-03-24 23:08
 **/
@Service
@Slf4j
public class StorehouseLogService {

    @Resource
    private AdminService adminService;

    @Resource
    private SrInstorehouseLogDao srInstorehouseLogDao;

    @Resource
    private SrTransferLogDao srTransferLogDao;

    @Resource
    private SrRefundLogDao srRefundLogDao;

    @Resource
    private SrOutstorehouseLogDao srOutstorehouseLogDao;

    /**
     * 入库记录查询 [如果当前管理员是仓库管理员 则只能看到自己仓库的情况]
     *
     * @param search 查询条件
     * @param userId 当前管理员id
     * @return
     */
    public QueryResponseResult<SrInstorehouseLogVO> getPurchaseLogByType(SearchInStorehouseLogDTO search, Long userId) {
        //获取当前用户的权限
        String filter = "1";
        //如果当前用户是仓库管理员
        if (isStoreRoomAdmin(userId)) {
            filter = "2";
        }
        PageHelper.startPage(search.getPageNum(), search.getPageSize());
        List<SrInstorehouseLog> list =
                srInstorehouseLogDao.getPurchaseLogByType(search, search.getType(), filter, userId);
        PageInfo<SrInstorehouseLog> pageInfo = new PageInfo<>(list);
        return StreamUtils.getPojoToVoToResponse(list, SrInstorehouseLogVO.class, pageInfo.getTotal());
    }

    /**
     * 查询调拨记录
     *
     * @param search 查询条件
     * @param userId 用户id
     * @return
     */
    public QueryResponseResult<SrTransferLogVO> getTransferLogByType(SearchTransferLogDTO search, Long userId) {
        //判断是否是仓库管理员
        List<SrTransferLog> list = Lists.newArrayList();
        //开始分页
        if (isStoreRoomAdmin(userId)) {
            PageHelper.startPage(search.getPageNum(), search.getPageSize());
            list = srTransferLogDao.getTransferLogByTypeStoreRoomAdmin(search, search.getType(), userId);
        } else {
            PageHelper.startPage(search.getPageNum(), search.getPageSize());
            list = srTransferLogDao.getTransferLogByType(search, search.getType());
        }
        PageInfo<SrTransferLog> pageInfo = new PageInfo<>(list);
        //封装返回对象
        return StreamUtils.getPojoToVoToResponse(list,SrTransferLogVO.class, pageInfo.getTotal());
    }

    /**
     * 查看该管理员是否是仓库管理员
     *
     * @param userId 管理员id
     * @return
     */
    public boolean isStoreRoomAdmin(Long userId) {
        SrRole adminRoleById = adminService.getAdminRoleById(userId);
        return adminRoleById.getId().equals(AdminRole.STOREROOM_ADMIN.getId());
    }

    /**
     * 获取退货记录
     *
     * @param search 查询条件
     * @param userId
     * @return
     */
    public QueryResponseResult<SrRefundLogVO> getRefundLogByType(SearchRefundLogDTO search, Long userId) {
        //判断是否是仓库管理员
        List<SrRefundLog> list = Lists.newArrayList();
        //开始分页
        if (isStoreRoomAdmin(userId)) {
            PageHelper.startPage(search.getPageNum(), search.getPageSize());
            list = srRefundLogDao.getRefundLogByTypeStoreRoomAdmin(search, search.getType(), userId);
        } else {
            PageHelper.startPage(search.getPageNum(), search.getPageSize());
            list = srRefundLogDao.getRefundLogByType(search, search.getType());
        }
        PageInfo<SrRefundLog> pageInfo = new PageInfo<>(list);
        //封装返回对象
        return StreamUtils.getPojoToVoToResponse(list,SrRefundLogVO.class, pageInfo.getTotal());
    }

    /**
     * 查询出库日志信息
     *
     * @param search 查询条件
     * @param userId 用户id
     * @return
     */
    public QueryResponseResult<SrOutstorehouseLogVO> getOutProductLogByType(SearchOutLogDTO search, Long userId) {
        //判断是否是仓库管理员
        List<SrOutstorehouseLog> list = Lists.newArrayList();
        //开始分页
        if (isStoreRoomAdmin(userId)) {
            PageHelper.startPage(search.getPageNum(), search.getPageSize());
            list = srOutstorehouseLogDao.getOutProductLogByTypeStoreRoomAdmin(search, search.getType(), userId);
        } else {
            PageHelper.startPage(search.getPageNum(), search.getPageSize());
            list = srOutstorehouseLogDao.getOutLogByType(search, search.getType());
        }
        PageInfo<SrOutstorehouseLog> pageInfo = new PageInfo<>(list);
        //封装返回对象
        return StreamUtils.getPojoToVoToResponse(list,SrOutstorehouseLogVO.class, pageInfo.getTotal());
    }

    /**
     * 入库统计
     *
     *
     * @param userId 管理员id
     * @param productId 产品id  可以不写
     * @return
     */
    public QueryResponseResult<StorehouseCountLogVO> getInStorehouseCountLog(Long productId, Long userId) {

        List<StorehouseCountLogVO> list = Lists.newArrayList();
        if (isStoreRoomAdmin(userId)) {
            list = srInstorehouseLogDao.getInStorehouseCountLogStoreRoomAdmin(productId,userId);
        }else {
            list = srInstorehouseLogDao.getInStorehouseCountLog(productId);
        }
        //获取月份与统计的对应关系
        Map<Integer, StorehouseCountLogVO> countLogVOMap = list.stream().collect(Collectors.toMap(StorehouseCountLogVO::getMonth, Function.identity()));
        //srInstorehouseLogDao.
       // DateUtil.offsetMonth(timeIndex,1);
        return StreamUtils.getSimpleResponse(getYearCount(countLogVOMap),12);
    }

    /**
     * 补全空的统计记录
     *
     * @param countLogVOMap
     * @return
     */
    public List<StorehouseCountLogVO> getYearCount(Map<Integer, StorehouseCountLogVO> countLogVOMap ){
        List<StorehouseCountLogVO> list  = new ArrayList<>(12);
        for (int i = 1; i < 13; i++) {
            //如果存在当月统计
            if (countLogVOMap.containsKey(i)){
                list.add(countLogVOMap.get(i));
            }else {
                StorehouseCountLogVO storehouseCountLogVO = new StorehouseCountLogVO();
                storehouseCountLogVO.setCount(0);
                storehouseCountLogVO.setMonth(i);
                list.add(storehouseCountLogVO);
            }
        }
        return list;

    }


    /**
     * 出库统计
     *
     * @param productId
     * @param userId
     * @return
     */
    public QueryResponseResult<StorehouseCountLogVO> getOutStorehouseCountLog(Long productId, Long userId) {

        List<StorehouseCountLogVO> list = Lists.newArrayList();
        if (isStoreRoomAdmin(userId)) {
            list = srOutstorehouseLogDao.getOutStorehouseCountLogStoreRoomAdmin(productId,userId);
        }else {
            list = srOutstorehouseLogDao.getOutStorehouseCountLog(productId);
        }
        //获取月份与统计的对应关系
        Map<Integer, StorehouseCountLogVO> countLogVOMap = list.stream().collect(Collectors.toMap(StorehouseCountLogVO::getMonth, Function.identity()));
        return StreamUtils.getSimpleResponse(getYearCount(countLogVOMap),12);
    }

    /**
     * 退货统计
     *
     * @param productId
     * @param userId
     * @return
     */
    public QueryResponseResult<StorehouseCountLogVO> getRefundStorehouseCountLog(Long productId, Long userId) {
        List<StorehouseCountLogVO> list = Lists.newArrayList();
        if (isStoreRoomAdmin(userId)) {
            list = srRefundLogDao.getRefundStorehouseCountLogStoreRoomAdmin(productId,userId);
        }else {
            list = srRefundLogDao.getRefundStorehouseCountLog(productId);
        }
        //获取月份与统计的对应关系
        Map<Integer, StorehouseCountLogVO> countLogVoMap = list.stream().collect(Collectors.toMap(StorehouseCountLogVO::getMonth, Function.identity()));
        return StreamUtils.getSimpleResponse(getYearCount(countLogVoMap),12);
    }


    /**
     * 库存调拨统计
     *
     * @param productId
     * @param userId
     * @return
     */
    public QueryResponseResult<StorehouseCountLogVO> getTransferStorehouseCountLog(Long productId, Long userId) {
        List<StorehouseCountLogVO> list = Lists.newArrayList();
        if (isStoreRoomAdmin(userId)) {
            list = srTransferLogDao.getTransferStorehouseCountLogStoreRoomAdmin(productId,userId);
        }else {
            list = srTransferLogDao.getTransferStorehouseCountLog(productId);
        }
        //获取月份与统计的对应关系
        Map<Integer, StorehouseCountLogVO> countLogVoMap = list.stream().collect(Collectors.toMap(StorehouseCountLogVO::getMonth, Function.identity()));
        return StreamUtils.getSimpleResponse(getYearCount(countLogVoMap),12);
    }
}
