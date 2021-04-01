package com.wxy.controller;

import com.wxy.config.response.QueryResponseResult;
import com.wxy.config.response.ResponseResult;
import com.wxy.model.SrAdmin;
import com.wxy.model.request.*;
import com.wxy.model.response.SrSimpleStorehouseVO;
import com.wxy.model.response.SrStorehouseVO;
import com.wxy.service.StoreHouseService;
import com.wxy.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : StoreHouseController
 * @packageName : com.wxy.controller
 * @description : 一般类
 * @date : 2021-03-24 15:03
 **/
@CrossOrigin("*")
@RestController
@Api(tags = "库房模块")
public class StoreHouseController {

    @Resource
    private StoreHouseService storeHouseService;
    @Resource
    private HttpServletRequest request;

    @ApiOperation(value = "超级管理员 新增一个仓库")
    @Secured("ROLE_SUP_ADMIN")
    @PostMapping("newStorehouse")
    public ResponseResult postNewStorehouse(@RequestBody NewStorehouseDTO newStorehouseDTO) {
        SrAdmin admin = JwtUtil.getAdmin(request);
        return storeHouseService.postNewStorehouse(newStorehouseDTO, admin.getId());
    }

    @ApiOperation(value = "超级管理员 更新仓库管理员")
    @Secured("ROLE_SUP_ADMIN")
    @PutMapping("changeStoreAdmin")
    public ResponseResult changeStoreAdmin(@RequestBody StoreAdminDTO storeAdminDTO) {

        return storeHouseService.changeStoreAdmin(storeAdminDTO);
    }

    @ApiOperation(value = "超级管理员 更新仓库状态")
    @Secured("ROLE_SUP_ADMIN")
    @PutMapping("changeStoreStatus")
    public ResponseResult changeStoreStatus(@RequestBody StoreStatusDTO storeAdminDTO) {
        return storeHouseService.changeStoreStatus(storeAdminDTO);
    }

    @ApiOperation(value = "查询库房列表", notes = "type = 0 全部 1 根据地点查询 2根据名称查询")
    @PostMapping("storehouseListByType")
    public QueryResponseResult<SrStorehouseVO> getStorehouseListByType(@RequestBody SearchStoreHouseDTO searchStoreHouseDTO) {

        return storeHouseService.getStorehouseListByType(searchStoreHouseDTO);
    }

    @ApiOperation(value = "仓库管理员进货")
    @PostMapping("purchase")
    public ResponseResult purchase(@RequestBody InStorehouseDTO storehouseDTO){
        SrAdmin admin = JwtUtil.getAdmin(request);
        return storeHouseService.purchase(storehouseDTO,admin.getId());
    }

    @ApiOperation(value = "库存查询",notes = "0 全部 1 根据原材料名称查询 2根据仓库id查询")
    @PostMapping("storeProductByType")
    public QueryResponseResult<StoreProductListDTO> getStoreProductByType(@RequestBody SearchStoreProductDTO search){
        SrAdmin admin = JwtUtil.getAdmin(request);
        return storeHouseService.getStoreProductByType(search,admin.getId());
    }

    @PostMapping("storeTransfer")
    @ApiOperation(value = "库存调拨")
    public ResponseResult storeTransfer(@RequestBody StoreTransferDTO storeTransferDTO){
        SrAdmin admin = JwtUtil.getAdmin(request);
        return storeHouseService.storeTransfer(storeTransferDTO,admin.getId());
    }

    @PostMapping("storeRefund")
    @ApiOperation(value = "退货")
    public ResponseResult storeRefund(@RequestBody RefundDTO refundDTO){
        SrAdmin admin = JwtUtil.getAdmin(request);
        return storeHouseService.storeRefund(refundDTO,admin.getId());
    }

    @ApiOperation(value = "查询所有库房列表 [type = 0 全部 1 没有绑定管理员的库房]", notes = "type = 0 全部 1 没有绑定管理员的库房")
    @GetMapping("allStorehouse/{type}")
    public QueryResponseResult<SrStorehouseVO> getAllStorehouse(@PathVariable String type){

        return storeHouseService.getAllStorehouse(type);
    }

    @ApiOperation(value = "返回没有绑定管理员的仓库列表", notes = "")
    @GetMapping("storehouseByBind")
    public QueryResponseResult<SrSimpleStorehouseVO> getStorehouseByBind(){
        return storeHouseService.getStorehouseByBind();
    }
    @ApiOperation(value = "返回所有仓库列表", notes = "")
    @GetMapping("storehouseAll")
    public QueryResponseResult<SrSimpleStorehouseVO> getStorehouseAll(){
        return storeHouseService.getStorehouseAll();
    }

}
