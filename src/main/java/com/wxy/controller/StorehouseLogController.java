package com.wxy.controller;

import com.wxy.config.response.QueryResponseResult;
import com.wxy.model.SrAdmin;
import com.wxy.model.request.log.SearchInStorehouseLogDTO;
import com.wxy.model.request.log.SearchOutLogDTO;
import com.wxy.model.request.log.SearchRefundLogDTO;
import com.wxy.model.request.log.SearchTransferLogDTO;
import com.wxy.model.response.log.*;
import com.wxy.service.StorehouseLogService;
import com.wxy.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : StorehouseLogController
 * @packageName : com.wxy.controller
 * @description : 一般类
 * @date : 2021-03-24 23:07
 **/
@CrossOrigin("*")
@RestController
@Api(tags = "日志模块")
@Slf4j
public class StorehouseLogController {

    @Resource
    private HttpServletRequest request;

    @Resource
    private StorehouseLogService storehouseLogService;

    @ApiOperation(value = "入库记录查询 [如果当前管理员是仓库管理员 则只能看到自己仓库的情况]")
    @PostMapping("purchaseLogByType")
    public QueryResponseResult<SrInstorehouseLogVO> getPurchaseLogByType(
            @RequestBody SearchInStorehouseLogDTO search) {
        SrAdmin admin = JwtUtil.getAdmin(request);
        return storehouseLogService.getPurchaseLogByType(search, admin.getId());
    }

    @ApiOperation(value = "调拨记录查询 [如果当前管理员是仓库管理员 则只能看到自己仓库的情况]")
    @PostMapping("transferLogByType")
    public QueryResponseResult<SrTransferLogVO> getTransferLogByType(
            @RequestBody SearchTransferLogDTO search){
        SrAdmin admin = JwtUtil.getAdmin(request);
        return storehouseLogService.getTransferLogByType(search,admin.getId());
    }

    @ApiOperation(value = "退货记录查询 [如果当前管理员是仓库管理员 则只能看到自己仓库的情况]")
    @PostMapping("refundLogByType")
    public QueryResponseResult<SrRefundLogVO> getRefundLogByType(
            @RequestBody SearchRefundLogDTO search){
        SrAdmin admin = JwtUtil.getAdmin(request);
        return storehouseLogService.getRefundLogByType(search,admin.getId());
    }

    @ApiOperation(value = "出库记录查询 [如果当前管理员是仓库管理员 则只能看到自己仓库的情况]")
    @PostMapping("outLogByType")
    public QueryResponseResult<SrOutstorehouseLogVO> getOutProductLogByType(
            @RequestBody SearchOutLogDTO search){
        SrAdmin admin = JwtUtil.getAdmin(request);
        return storehouseLogService.getOutProductLogByType(search,admin.getId());
    }

    @ApiOperation(value = "入库统计 [产品id 不写 就是统计所有] ")
    @GetMapping(value = {"inStorehouseCount/{productId}","inStorehouseCount"})
    public QueryResponseResult<StorehouseCountLogVO> getInStorehouseCountLog(
            @PathVariable(value = "productId",required = false) Long productId){
        SrAdmin admin = JwtUtil.getAdmin(request);
        return storehouseLogService.getInStorehouseCountLog(productId,admin.getId());
    }

    @ApiOperation(value = "出库统计 [产品id 不写 就是统计所有] ")
    @GetMapping(value = {"outStorehouseCount/{productId}","outStorehouseCount"})
    public QueryResponseResult<StorehouseCountLogVO> getOutStorehouseCountLog(
            @PathVariable(value = "productId",required = false) Long productId){
        SrAdmin admin = JwtUtil.getAdmin(request);
        return storehouseLogService.getOutStorehouseCountLog(productId,admin.getId());
    }


    @ApiOperation(value = "退货统计 [产品id 不写 就是统计所有] ")
    @GetMapping(value = {"refundStorehouseCount/{productId}","refundStorehouseCount"})
    public QueryResponseResult<StorehouseCountLogVO> getRefundStorehouseCountLog(
            @PathVariable(value = "productId",required = false) Long productId){
        SrAdmin admin = JwtUtil.getAdmin(request);
        return storehouseLogService.getRefundStorehouseCountLog(productId,admin.getId());
    }

    @ApiOperation(value = "库存调拨统计 [产品id 不写 就是统计所有] ")
    @GetMapping(value = {"transferStorehouseCount/{productId}","transferStorehouseCount"})
    public QueryResponseResult<StorehouseCountLogVO> getTransferStorehouseCountLog(
            @PathVariable(value = "productId",required = false) Long productId){
        SrAdmin admin = JwtUtil.getAdmin(request);
        return storehouseLogService.getTransferStorehouseCountLog(productId,admin.getId());
    }
}
