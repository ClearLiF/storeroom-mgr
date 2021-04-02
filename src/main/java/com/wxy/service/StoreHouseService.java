package com.wxy.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.wxy.config.response.QueryResponseResult;
import com.wxy.config.response.ResponseResult;
import com.wxy.dao.*;
import com.wxy.exception.ExceptionCast;
import com.wxy.handler.SrAdminAspect;
import com.wxy.handler.SrAdminLogAspect;
import com.wxy.model.*;
import com.wxy.model.enums.ProductStatusEnum;
import com.wxy.model.enums.RefundStatusEnum;
import com.wxy.model.enums.SrStatusEnum;
import com.wxy.model.enums.TransferStatusEnum;
import com.wxy.model.request.*;
import com.wxy.model.response.ProductCode;
import com.wxy.model.response.SrSimpleStorehouseVO;
import com.wxy.model.response.SrStorehouseVO;
import com.wxy.utils.StreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : wxy
 * @version : V1.0
 * @className : StoreHouseService
 * @packageName : com.wxy.service
 * @description : 一般类
 * @date : 2021-03-24 15:05
 **/
@Service
@Slf4j
public class StoreHouseService {
    @Resource
    private SrStorehouseDao srStorehouseDao;

    @Resource
    private SrStorehouseProductDao srStorehouseProductDao;
    @Resource
    private SrOriginalProductDao srOriginalProductDao;

    @Resource
    private SrInstorehouseLogDao srInstorehouseLogDao;

    @Resource
    private AdminService adminService;

    @Resource
    private StoreHouseService storeHouseService;


    @Resource
    private SrTransferLogDao srTransferLogDao;

    @Resource
    private SrRefundLogDao srRefundLogDao;

    /**
     * 超级管理员 新增一个仓库
     *
     * @param newStorehouseDTO 仓库信息
     * @param userId           管理员id
     * @return
     */
    public ResponseResult postNewStorehouse(NewStorehouseDTO newStorehouseDTO, Long userId) {
        SrStorehouse srStorehouse = new SrStorehouse();
        //拷贝值信息
        BeanUtils.copyProperties(newStorehouseDTO, srStorehouse);
        //设置启用状态
        srStorehouse.setSrStatus(SrStatusEnum.USE);
        srStorehouse.setSrRemainSize(srStorehouse.getSrSize());
        srStorehouse.setUserId(0L);
        //设置创建时间
        srStorehouse.setCreateTime(System.currentTimeMillis());
        srStorehouse.setUpdateTime(System.currentTimeMillis());
        srStorehouseDao.insert(srStorehouse);
        return ResponseResult.success();
    }

    /**
     * 更换仓库管理员
     *
     * @param storeAdminDTO 更换仓库信息
     * @return
     */
    @SrAdminLogAspect(userId = "storeAdminDTO#adminId", operateName = SrAdminAspect.AdminAction.CHANGE_ADMIN)
    public ResponseResult changeStoreAdmin(StoreAdminDTO storeAdminDTO) {
        //更新仓库管理员信息
        srStorehouseDao.update(null, new LambdaUpdateWrapper<SrStorehouse>()
                .set(SrStorehouse::getUserId, storeAdminDTO.getAdminId())
                .set(SrStorehouse::getUpdateTime, System.currentTimeMillis())
                .eq(SrStorehouse::getId, storeAdminDTO.getStoreId()));
        return ResponseResult.success();
    }

    /**
     * 更换仓库启用状态
     *
     * @param storeAdminDTO 更换仓库信息
     * @return
     */
    public ResponseResult changeStoreStatus(StoreStatusDTO storeAdminDTO) {
        srStorehouseDao.update(null, new LambdaUpdateWrapper<SrStorehouse>()
                .set(SrStorehouse::getSrStatus, storeAdminDTO.getStatus().getValue())
                .set(SrStorehouse::getUpdateTime, System.currentTimeMillis())
                .eq(SrStorehouse::getId, storeAdminDTO.getStoreId()));
        return ResponseResult.success();
    }

    /**
     * 根据类型查询仓库列表
     *
     * @param searchStoreHouseDTO 仓库列表
     * @return
     */
    public QueryResponseResult<SrStorehouseVO> getStorehouseListByType(SearchStoreHouseDTO searchStoreHouseDTO) {
        //开启分页查询
        PageHelper.startPage(searchStoreHouseDTO.getPageNum(), searchStoreHouseDTO.getPageSize());
        //根据类型查询
        List<SrStorehouse> storehouseList = srStorehouseDao.getStorehouseListByType(searchStoreHouseDTO.getType(), searchStoreHouseDTO);
        PageInfo<SrStorehouse> storehousePageInfo = new PageInfo<>(storehouseList);
        return StreamUtils.getPojoToVoToResponse(storehouseList, SrStorehouseVO.class, storehousePageInfo.getTotal());
    }

    /**
     * 获取仓库管理员下的所有仓库信息
     *
     * @return
     */
    public QueryResponseResult<SrStorehouseVO> getStorehouseByAdmin(Long userId) {
        //根据管理员id查询仓库信息
        List<SrStorehouse> storehouseList = srStorehouseDao
                .selectList(new LambdaQueryWrapper<SrStorehouse>().eq(SrStorehouse::getUserId, userId));
        return StreamUtils.getPojoToVoToResponse(storehouseList, SrStorehouseVO.class, storehouseList.size());
    }

    /**
     * 管理员进货
     *
     * @param storehouseDTO 进货
     * @param userId        管理员id
     * @return
     */
    @Transactional
    public ResponseResult purchase(InStorehouseDTO storehouseDTO, Long userId) {
        //计算这批货需要占的空间
        SrOriginalProduct product = srOriginalProductDao.selectOne(new LambdaQueryWrapper<SrOriginalProduct>()
                .eq(SrOriginalProduct::getId, storehouseDTO.getProductId()));
        //如果该货物没有查到信息 则返回错误信息 提示让管理员添加
        if (product == null) {
            return new ResponseResult(ProductCode.NO_SUCH_GOODS);
        }
        //计算货物所占空间
        int size = product.getProductSize() * storehouseDTO.getProductNum();
        //查询仓库信息
        SrStorehouse srStorehouse = srStorehouseDao.selectOne(new LambdaQueryWrapper<SrStorehouse>()
                .eq(SrStorehouse::getId, storehouseDTO.getStorehouseId()));
        //判断仓库是否启用
        if (srStorehouse.getSrStatus().equals(SrStatusEnum.UN_USE)) {
            //返回该仓库已经停用
            return new ResponseResult(ProductCode.WAREHOUSE_DECOMMISSIONED);
        }
        //判断该仓库是否还有空余位置放置该货物
        if (srStorehouse.getSrRemainSize() < size) {
            return new ResponseResult(ProductCode.NO_SPACE);
        }
        //减少仓库位置
        srStorehouseDao.update(null, new LambdaUpdateWrapper<SrStorehouse>()
                .set(SrStorehouse::getSrRemainSize, srStorehouse.getSrRemainSize() - size)
                .eq(SrStorehouse::getId, storehouseDTO.getStorehouseId()));
        //开始生成入库日志
        SrInstorehouseLog srInstorehouseLog = new SrInstorehouseLog();
        //设置实付金额
        srInstorehouseLog.setPayNum(storehouseDTO.getProductNum() * product.getProductPrice());
        srInstorehouseLog.setCreateTime(System.currentTimeMillis());
        srInstorehouseLog.setUpdateTime(System.currentTimeMillis());
        srInstorehouseLog.setProductId(storehouseDTO.getProductId());
        srInstorehouseLog.setProductNum(storehouseDTO.getProductNum());
        srInstorehouseLog.setStorehouseId(storehouseDTO.getStorehouseId());
        srInstorehouseLog.setProductStatus(ProductStatusEnum.STORED);
        srInstorehouseLog.setUserId(userId);
        srInstorehouseLogDao.insert(srInstorehouseLog);
        //仓库货物增加
        purchaseToStoreHouse(storehouseDTO);

        return ResponseResult.success();
    }

    /**
     * 原材料入库
     *
     * @param storehouseDTO 原材料情况
     */
    private void purchaseToStoreHouse(InStorehouseDTO storehouseDTO) {
        SrStorehouseProduct srStorehouseProduct = srStorehouseProductDao.selectOne(new LambdaQueryWrapper<SrStorehouseProduct>()
                .eq(SrStorehouseProduct::getStorehouseId, storehouseDTO.getStorehouseId())
                .eq(SrStorehouseProduct::getProductId, storehouseDTO.getProductId()));
        //如果该仓库还没有这个原材料的信息
        if (srStorehouseProduct == null) {
            srStorehouseProduct = new SrStorehouseProduct();
            srStorehouseProduct.setProductId(storehouseDTO.getProductId());
            srStorehouseProduct.setProductNum(storehouseDTO.getProductNum());
            srStorehouseProduct.setStorehouseId(storehouseDTO.getStorehouseId());
            srStorehouseProduct.setCreateTime(System.currentTimeMillis());
            srStorehouseProduct.setUpdateTime(System.currentTimeMillis());
            srStorehouseProductDao.insert(srStorehouseProduct);
        } else {
            //如果有这个原材料 则直接新增
            srStorehouseProduct.setProductNum(srStorehouseProduct.getProductNum() + storehouseDTO.getProductNum());
            srStorehouseProduct.setUpdateTime(System.currentTimeMillis());
            srStorehouseProductDao.updateById(srStorehouseProduct);
        }
    }

    /**
     * 库存查询 根据类型查询 [当管理员是超级管理员 则返回所有仓库信息  如果管理员是仓库管理员则只返回自己管理的仓库信息 ]
     *
     * @param search 查询条件
     * @param userId 当前管理员id
     * @return
     */
    public QueryResponseResult<StoreProductListDTO> getStoreProductByType(SearchStoreProductDTO search, Long userId) {

        //获取该管理员的权限
        SrRole adminRoleById = adminService.getAdminRoleById(userId);
        List<SrStorehouseProduct> list = Lists.newArrayList();
        PageHelper.startPage(search.getPageNum(), search.getPageSize());
        //判断是否是仓库管理员
        if (adminRoleById.getId().equals(AdminRole.STOREROOM_ADMIN.getId())) {
            //只查询自己的仓库信息
            list = srStorehouseProductDao.getStoreProductByTypeStoreAdmin(search, search.getType(), userId);
        } else {
            //查询所有仓库信息
            list = srStorehouseProductDao.getStoreProductByTypeSuperAdmin(search, search.getType());
        }
        PageInfo<SrStorehouseProduct> pageInfo = new PageInfo<>(list);
        //封装返回信息
        return StreamUtils.getPojoToVoToResponse(list, StoreProductListDTO.class, pageInfo.getTotal());
    }


    /**
     * 库存调拨
     *
     * @param storeTransferDTO 库存调拨信息
     * @param userId           操作员id
     * @return
     */
    @Transactional
    public ResponseResult storeTransfer(StoreTransferDTO storeTransferDTO, Long userId) {
        //判断该仓库是否有足够多的货物 如果有直接返回库存信息
        SrStorehouseProduct srStorehouseProduct = hasEnoughProduct(storeTransferDTO.getProductId(),
                storeTransferDTO.getFormStorehouseId(), storeTransferDTO.getProductNum());
        //查询该商品的信息
        SrOriginalProduct product = srOriginalProductDao.selectOne(new LambdaQueryWrapper<SrOriginalProduct>()
                .eq(SrOriginalProduct::getId, storeTransferDTO.getProductId()));
        //计算货物所占空间
        int size = product.getProductSize() * storeTransferDTO.getProductNum();
        //查询仓库信息
        SrStorehouse toSrStorehouse = getSrStorehouseById(storeTransferDTO.getToStorehouseId());
        SrStorehouse fromSrStorehouse = getSrStorehouseById(storeTransferDTO.getFormStorehouseId());
        //判断该仓库是否还有空余位置放置该货物
        if (toSrStorehouse.getSrRemainSize() < size) {
            return new ResponseResult(ProductCode.NO_SPACE);
        }
        //开始入库
        //减少库存
        srStorehouseProductDao.update(null, new LambdaUpdateWrapper<SrStorehouseProduct>()
                .set(SrStorehouseProduct::getProductNum, srStorehouseProduct.getProductNum() - storeTransferDTO.getProductNum())
                .set(SrStorehouseProduct::getUpdateTime, System.currentTimeMillis())
                .eq(SrStorehouseProduct::getProductId, storeTransferDTO.getProductId()));

        //减少仓库位置
        srStorehouseDao.update(null, new LambdaUpdateWrapper<SrStorehouse>()
                .set(SrStorehouse::getSrRemainSize, toSrStorehouse.getSrRemainSize() - size)
                .eq(SrStorehouse::getId, storeTransferDTO.getToStorehouseId()));
        //增加初始库的位置
        srStorehouseDao.update(null, new LambdaUpdateWrapper<SrStorehouse>()
                .set(SrStorehouse::getSrRemainSize, fromSrStorehouse.getSrRemainSize() + size)
                .eq(SrStorehouse::getId, storeTransferDTO.getFormStorehouseId()));
        //转入另一个仓库
        InStorehouseDTO inStorehouseDTO = new InStorehouseDTO();
        inStorehouseDTO.setStorehouseId(storeTransferDTO.getToStorehouseId());
        inStorehouseDTO.setProductId(storeTransferDTO.getProductId());
        inStorehouseDTO.setProductNum(storeTransferDTO.getProductNum());
        purchaseToStoreHouse(inStorehouseDTO);

        //写入调拨表单
        SrTransferLog srTransferLog = new SrTransferLog();
        srTransferLog.setProductId(storeTransferDTO.getProductId());
        srTransferLog.setProductNum(storeTransferDTO.getProductNum());
        srTransferLog.setFromStorehouseId(storeTransferDTO.getFormStorehouseId());
        srTransferLog.setToStorehouseId(storeTransferDTO.getToStorehouseId());
        srTransferLog.setUserId(userId);
        srTransferLog.setCreateTime(System.currentTimeMillis());
        srTransferLog.setUpdateTime(System.currentTimeMillis());
        srTransferLog.setTransferStatus(TransferStatusEnum.STORED);
        srTransferLogDao.insert(srTransferLog);
        return ResponseResult.success();
    }

    /**
     * 采购退货
     *
     * @param refundDTO 退货内容
     * @param userId    管理员id
     * @return
     */
    public ResponseResult storeRefund(RefundDTO refundDTO, Long userId) {
        //判断该仓库是否有足够多的货物可以退货  如果可以直接返回库存信息
        SrStorehouseProduct srStorehouseProduct = hasEnoughProduct(refundDTO.getProductId(), refundDTO.getStorehouseId(), refundDTO.getProductNum());
        //查询仓库信息
        SrStorehouse srStorehouseById = getSrStorehouseById(refundDTO.getStorehouseId());
        //减少库存信息
        reduceProduct(refundDTO.getProductId(), refundDTO.getProductNum(), refundDTO.getStorehouseId());
        //记录退货日志
        SrRefundLog srRefundLog = new SrRefundLog();
        BeanUtils.copyProperties(refundDTO,srRefundLog);
        srRefundLog.setRefundStatus(RefundStatusEnum.STORED);
        srRefundLog.setCreateTime(System.currentTimeMillis());
        srRefundLog.setUpdateTime(System.currentTimeMillis());
        srRefundLog.setUserId(userId);
        srRefundLogDao.insert(srRefundLog);
        return ResponseResult.success();
    }

    /**
     * 查询仓库是否有足够多的货物
     *
     * @param productId    货物id
     * @param storehouseId 仓库id
     * @param productNum   要转移的货物数量
     * @return
     */
    public SrStorehouseProduct hasEnoughProduct(Long productId, Long storehouseId, Integer productNum) {
        SrStorehouseProduct srStorehouseProduct = srStorehouseProductDao.selectOne(new LambdaQueryWrapper<SrStorehouseProduct>()
                .eq(SrStorehouseProduct::getStorehouseId, storehouseId)
                .eq(SrStorehouseProduct::getProductId, productId));
        //判断转出数量是否符合要求
        if (srStorehouseProduct == null || srStorehouseProduct.getProductNum() < productNum) {
            ExceptionCast.cast(ProductCode.NO_GOODS);
        }
        return srStorehouseProduct;
    }

    /**
     * 查询仓库信息
     *
     * @param storehouseId 仓库id
     * @return
     */
    public SrStorehouse getSrStorehouseById(Long storehouseId) {
        SrStorehouse fromSrStorehouse = srStorehouseDao.selectOne(new LambdaQueryWrapper<SrStorehouse>()
                .eq(SrStorehouse::getId, storehouseId));
        //判断仓库是否启用
        if (fromSrStorehouse.getSrStatus().equals(SrStatusEnum.UN_USE)) {
            //返回该仓库已经停用
            ExceptionCast.cast(ProductCode.WAREHOUSE_DECOMMISSIONED);
        }

        return fromSrStorehouse;
    }

    /**
     * 减少货物的数量
     *
     * @param productId    产品id
     * @param productNum   产品数量
     * @param storehouseId 仓库id
     */
    public void reduceProduct(Long productId, Integer productNum, Long storehouseId) {
        //减少库存
        srStorehouseProductDao.reduceProduct(productId, productNum, storehouseId, System.currentTimeMillis());
        SrOriginalProduct product = srOriginalProductDao.selectOne(new LambdaQueryWrapper<SrOriginalProduct>()
                .eq(SrOriginalProduct::getId, productId));

        int size = product.getProductSize() * productNum;
        //增加仓库位置
        srStorehouseDao.reduceProduct(storehouseId, size, System.currentTimeMillis());
    }

    public QueryResponseResult<SrStorehouseVO> getAllStorehouse(String type) {
        List<SrStorehouse> storehouseList =null;

        if ("0".equals(type)){
             storehouseList = srStorehouseDao.selectList(
                    new LambdaQueryWrapper<SrStorehouse>());
        }else {
            storehouseList = srStorehouseDao.selectList(
                    new LambdaQueryWrapper<SrStorehouse>().eq(SrStorehouse::getUserId,0));
        }
      return StreamUtils.getPojoToVoToResponse(storehouseList,SrStorehouseVO.class,storehouseList.size());
    }

    /**
     * 查询没有绑定管理员的仓库
     *
     * @return
     */
    public QueryResponseResult<SrSimpleStorehouseVO> getStorehouseByBind() {
        //查询没有绑定管理员的仓库
        List<SrStorehouse> storehouseList = srStorehouseDao.selectList(new LambdaQueryWrapper<SrStorehouse>()
                .select(SrStorehouse::getId, SrStorehouse::getSrName).eq(SrStorehouse::getUserId, 0));
        return StreamUtils.getPojoToVoToResponse(storehouseList,SrSimpleStorehouseVO.class,storehouseList.size());
    }

    /**
     * 查询所有仓库列表
     *
     * @return
     * @param userId
     */
    public QueryResponseResult<SrSimpleStorehouseVO> getStorehouseAll(Long userId) {

        //获取该管理员的权限
        SrRole adminRoleById = adminService.getAdminRoleById(userId);
        List<SrStorehouseProduct> list = Lists.newArrayList();
        List<SrStorehouse> storehouseList =null;
        //判断是否是仓库管理员
        if (adminRoleById.getId().equals(AdminRole.STOREROOM_ADMIN.getId())) {
            //只查询自己的仓库信息
            storehouseList = srStorehouseDao.selectList(new LambdaQueryWrapper<SrStorehouse>()
                    .select(SrStorehouse::getId, SrStorehouse::getSrName)
                    .eq(SrStorehouse::getUserId,userId)
                    .eq(SrStorehouse::getSrStatus,SrStatusEnum.USE.getValue()));
        } else {
            //查询所有仓库信息
            storehouseList = srStorehouseDao.selectList(new LambdaQueryWrapper<SrStorehouse>()
                    .select(SrStorehouse::getId, SrStorehouse::getSrName).eq(SrStorehouse::getSrStatus,SrStatusEnum.USE.getValue()));
        }
        return StreamUtils.getPojoToVoToResponse(storehouseList,SrSimpleStorehouseVO.class,storehouseList.size());

    }
}
