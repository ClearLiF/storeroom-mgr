package com.wxy.controller;

import com.wxy.config.response.QueryResponseResult;
import com.wxy.config.response.ResponseResult;
import com.wxy.model.request.SearchProductDTO;
import com.wxy.model.request.SrOriginalProductDTO;
import com.wxy.model.request.UpdateOriginalProductDTO;
import com.wxy.model.response.SrOriginalProductVO;
import com.wxy.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author : wxy
 * @version : V1.0
 * @className : ProductController
 * @packageName : com.wxy.controller
 * @description : 一般类
 * @date : 2021-03-20 15:33
 **/
@RestController
@CrossOrigin("*")
@Api(tags = "原材料模块")
public class ProductController {

    @Resource
    private ProductService productService;

    /**
     * 新增可以入库的产品
     *
     * @param srOriginalProductDTO 入库的产品内容
     * @return
     */
    @ApiOperation(value = "新增可以入库的产品", notes = "新增")
    @PostMapping("originalProduct")
    public ResponseResult putProduct(@RequestBody SrOriginalProductDTO srOriginalProductDTO) {
        return productService.putNewProduct(srOriginalProductDTO);
    }

    @ApiOperation(value = "查询原材料商品列表", notes = "0 全部 1 根据商品名称查询")
    @PostMapping("originalProductSearch")
    public QueryResponseResult<SrOriginalProductVO> getProduct(@RequestBody SearchProductDTO searchProductDTO) {
        return productService.getProductList(searchProductDTO);
    }

    @ApiOperation(value = "更新原材料信息 没有的可以不写", notes = "")
    @PutMapping("originalProduct")
    public ResponseResult updateProduct(@RequestBody UpdateOriginalProductDTO srOriginalProductDTO){
        return productService.updateProduct(srOriginalProductDTO);
    }

    @ApiOperation(value = "删除原材料信息", notes = "")
    @DeleteMapping("originalProduct/{productId}")
    public ResponseResult deleteProduct(@PathVariable Long productId){
        return productService.deleteProduct(productId);
    }


    @ApiOperation(value = "原材料列表[全部]")
    @GetMapping("originalProductList")
    public QueryResponseResult<SrOriginalProductVO> getProductList(){
        return productService.getProductList2();
    }


}
