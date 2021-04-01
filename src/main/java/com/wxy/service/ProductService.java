package com.wxy.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wxy.config.response.QueryResponseResult;
import com.wxy.config.response.ResponseResult;
import com.wxy.dao.SrOriginalProductDao;
import com.wxy.model.SrOriginalProduct;
import com.wxy.model.enums.DeleteMarkEnum;
import com.wxy.model.request.SearchProductDTO;
import com.wxy.model.request.SrOriginalProductDTO;
import com.wxy.model.request.UpdateOriginalProductDTO;
import com.wxy.model.response.SrOriginalProductVO;
import com.wxy.utils.JwtUtil;
import com.wxy.utils.StreamUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : ProductService
 * @packageName : com.wxy.service
 * @description : 一般类
 * @date : 2021-03-20 15:41
 **/
@Service
public class ProductService {

    @Resource
    private SrOriginalProductDao srOriginalProductDao;

    /**
     * 新增一个商品
     *
     * @param srOriginalProductDTO 商品内容
     * @return
     */
    public ResponseResult putNewProduct(SrOriginalProductDTO srOriginalProductDTO) {
        long userId = JwtUtil.getAdminPrincipal();
        SrOriginalProduct product = new SrOriginalProduct();
        BeanUtils.copyProperties(srOriginalProductDTO, product);
        product.setUserId(userId);
        product.setCreateTime(System.currentTimeMillis());
        product.setUpdateTime(System.currentTimeMillis());
        srOriginalProductDao.insert(product);
        return ResponseResult.success();
    }

    /**
     * 根据类型获取原材料列表
     *
     * @param searchProductDTO 查询条件
     * @return
     */
    public QueryResponseResult<SrOriginalProductVO> getProductList(SearchProductDTO searchProductDTO) {

        PageHelper.startPage(searchProductDTO.getPageNum(), searchProductDTO.getPageSize());
        List<SrOriginalProduct> list =
                srOriginalProductDao.getProductList(searchProductDTO.getType(), searchProductDTO);

        PageInfo<SrOriginalProduct> srOriginalProductPageInfo = new PageInfo<>(list);
        return StreamUtils.getPojoToVoToResponse(list, SrOriginalProductVO.class, srOriginalProductPageInfo.getTotal());
    }

    /**
     * 更新原材料内容
     *
     * @param srOriginalProductDTO 更新信息
     * @return
     */
    public ResponseResult updateProduct(UpdateOriginalProductDTO srOriginalProductDTO) {

        SrOriginalProduct product = new SrOriginalProduct();
        //拷贝信息 准备更新
        BeanUtils.copyProperties(srOriginalProductDTO, product);
        product.setUpdateTime(System.currentTimeMillis());
        srOriginalProductDao.updateById(product);
        return ResponseResult.success();
    }

    /**
     * 删除原材料
     *
     * @param productId
     * @return
     */
    public ResponseResult deleteProduct(Long productId) {
        srOriginalProductDao.update(null, new LambdaUpdateWrapper<SrOriginalProduct>()
                .set(SrOriginalProduct::getDeleteMark, DeleteMarkEnum.IS_DELETE.getValue())
                .eq(SrOriginalProduct::getId, productId));
        return ResponseResult.success();
    }

    /**
     * 获取所有原材料列表
     *
     * @return
     */
    public QueryResponseResult<SrOriginalProductVO> getProductList2() {
        List<SrOriginalProduct> list =
                srOriginalProductDao.getProductList("2",null);
        return StreamUtils.getPojoToVoToResponse(list,SrOriginalProductVO.class,list.size());
    }
}
