package com.wxy.controller;

import com.wxy.config.response.QueryResponseResult;
import com.wxy.config.response.ResponseResult;
import com.wxy.model.SrAdmin;
import com.wxy.model.request.endProduct.*;
import com.wxy.model.response.endProduct.EndProductVO;
import com.wxy.service.EndProductService;
import com.wxy.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : EndProductController
 * @packageName : com.wxy.controller
 * @description : 一般类
 * @date : 2021-03-25 21:58
 **/
@RestController
@Slf4j
@CrossOrigin("*")
@Api(tags = "成品模块")
public class EndProductController {

    @Resource
    private EndProductService endProductService;

    @Resource
    private HttpServletRequest request;

    @Secured("ROLE_SUP_ADMIN")
    @ApiOperation(value = "新增成品", notes = "新增")
    @PostMapping("newEndProduct")
    public ResponseResult putNewEndProduct(@RequestBody EndProductDTO endProductDTO) {
        SrAdmin admin = JwtUtil.getAdmin(request);
        return endProductService.putNewEndProduct(endProductDTO,admin.getId());
    }

    @ApiOperation(value = "查询成品列表", notes = " 0 全部 1.根据成品名称查询")
    @PostMapping("endProductByType")
    public QueryResponseResult<EndProductVO> getEndProductByType(@RequestBody SearchEndProductDTO search){
        SrAdmin admin = JwtUtil.getAdmin(request);
        return endProductService.getEndProductByType(search,admin.getId());
    }

    @ApiOperation(value = "删除成品的一个原材料对应关系", notes = "原材料id")
    @DeleteMapping("endProductMap/{productId}")
    public ResponseResult deleteProductMap(@PathVariable Long productId){
        return endProductService.deleteEndProductMap(productId);
    }


    @ApiOperation(value = "删除成品", notes = "原材料id")
    @DeleteMapping("endProduct/{productId}")
    public ResponseResult deleteEndProduct(@PathVariable Long productId){
        return endProductService.deleteEndProduct(productId);
    }

    @ApiOperation(value = "更新成品 [没有的可以不写]", notes = "没有的可以不写")
    @PutMapping("endProduct")
    public ResponseResult updateEndProduct(@RequestBody EditEndProductDTO endProductDTO){

        return endProductService.updateEndProduct(endProductDTO);
    }

    @ApiOperation(value = "生产出库")
    @PutMapping("produceEndProduct")
    public ResponseResult endProductProduce(
            @RequestBody EndProductProduceDTO endProductProduceDTO){
        SrAdmin admin = JwtUtil.getAdmin(request);
        return endProductService.endProductProduce(endProductProduceDTO,admin.getId());
    }


    @ApiOperation(value = "更改成品与原材料对应关系的数量")
    @PutMapping("produceEndProductMap")
    public ResponseResult updateEndProductMap(@RequestBody EndProductMapDTO endProductMapDTO){
       return endProductService.updateEndProductMap(endProductMapDTO);
    }
}
