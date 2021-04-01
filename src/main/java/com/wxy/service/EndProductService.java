package com.wxy.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wxy.config.response.QueryResponseResult;
import com.wxy.config.response.ResponseResult;
import com.wxy.dao.SrEndProductDao;
import com.wxy.dao.SrOutstorehouseLogDao;
import com.wxy.dao.SrProductMapDao;
import com.wxy.dao.SrStorehouseProductDao;
import com.wxy.exception.ExceptionCast;
import com.wxy.model.SrEndProduct;
import com.wxy.model.SrOutstorehouseLog;
import com.wxy.model.SrProductMap;
import com.wxy.model.SrStorehouseProduct;
import com.wxy.model.enums.DeleteMarkEnum;
import com.wxy.model.enums.OutStatusEnum;
import com.wxy.model.request.endProduct.*;
import com.wxy.model.response.endProduct.EndProductVO;
import com.wxy.model.response.ProductCode;
import com.wxy.utils.CollectionUtil;
import com.wxy.utils.StreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : EndProductService
 * @packageName : com.wxy.service
 * @description : 一般类
 * @date : 2021-03-25 22:32
 **/
@Service
@Slf4j
public class EndProductService {

    @Resource
    private SrEndProductDao srEndProductDao;

    @Resource
    private SrProductMapDao srProductMapDao;

    @Resource
    private SrStorehouseProductDao srStorehouseProductDao;

    @Resource
    private StoreHouseService storeHouseService;

    @Resource
    private SrOutstorehouseLogDao srOutstorehouseLogDao;

    /**
     * 新增一个产品
     *
     * @param endProductDTO 产品内容
     * @param userId        用户id
     * @return
     */
    @Transactional
    public ResponseResult putNewEndProduct(EndProductDTO endProductDTO, Long userId) {

        if (CollectionUtil.isEmpty(endProductDTO.getOriginalProductList())) {
            return new ResponseResult(ProductCode.NO_SPACE);
        }
        //新增
        SrEndProduct srEndProduct = new SrEndProduct();
        BeanUtils.copyProperties(endProductDTO, srEndProduct);
        srEndProduct.setUserId(userId);
        srEndProduct.setCreateTime(System.currentTimeMillis());
        srEndProduct.setUpdateTime(System.currentTimeMillis());
        srEndProductDao.insert(srEndProduct);
        //新增产品与原材料的对应关系
        for (OriginalProduct originalProduct : endProductDTO.getOriginalProductList()) {
            SrProductMap srProductMap = new SrProductMap();
            srProductMap.setEndProductId(srEndProduct.getId());
            srProductMap.setProductNum(originalProduct.getProductNum());
            srProductMap.setOriginalProductId(originalProduct.getProductId());
            srProductMap.setCreateTime(System.currentTimeMillis());
            srProductMap.setUpdateTime(System.currentTimeMillis());
            srProductMapDao.insert(srProductMap);
        }
        return ResponseResult.success();
    }

    /**
     * 根据类型查询成品
     *
     * @param search
     * @param userId
     * @return
     */
    public QueryResponseResult<EndProductVO> getEndProductByType(SearchEndProductDTO search, Long userId) {

        //自定义分页
        int skip = (search.getPageNum() - 1) * search.getPageSize();
        //分页查询
        List<SrEndProduct> list = srEndProductDao.getEndProductByType(search, search.getType(), skip, search.getPageSize());
        //计算总数
        int count = srEndProductDao.selectCountByType(search, search.getType());
        return StreamUtils.getPojoToVoToResponse(list, EndProductVO.class, count);
    }

    /**
     * 删除原材料对应关系
     *
     * @param productId
     * @return
     */
    public ResponseResult deleteEndProductMap(Long productId) {

        srProductMapDao.deleteById(productId);
        return ResponseResult.success();
    }

    /**
     * 删除成品
     *
     * @param productId
     * @return
     */
    public ResponseResult deleteEndProduct(Long productId) {
        srEndProductDao.update(null, new LambdaUpdateWrapper<SrEndProduct>()
                .set(SrEndProduct::getDeleteMark, DeleteMarkEnum.IS_DELETE.getValue())
                .eq(SrEndProduct::getId, productId));
        return ResponseResult.success();
    }

    /**
     * 更新一个成品
     *
     * @param endProductDTO
     * @return
     */
    public ResponseResult updateEndProduct(EditEndProductDTO endProductDTO) {
        SrEndProduct srEndProduct = new SrEndProduct();

        BeanUtils.copyProperties(endProductDTO, srEndProduct);
        srEndProductDao.updateById(srEndProduct);
        //更新原材料与原料的对应
        List<EndProductMapDTO> endProductMapList = endProductDTO.getEndProductMapList();
        if (CollectionUtil.isNotEmpty(endProductMapList)){
            //更新原材料与原料的对应关系
            endProductMapList.forEach(this::updateEndProductMap);
        }
        return ResponseResult.success();
    }

    /**
     * 生产出库
     *
     * @param endProductProduceDTO 出库内容
     * @param userId               管理员id
     * @return
     */
    @Transactional
    public ResponseResult endProductProduce(EndProductProduceDTO endProductProduceDTO, Long userId) {

        //查询生产所需要的原材料列表
        List<SrProductMap> produceList = srProductMapDao.getEndProductProduce(endProductProduceDTO.getEndProductId());

        if (CollectionUtil.isEmpty(produceList)) {
            return new ResponseResult(ProductCode.NO_SUCH_GOODS);
        }
        //查询仓库的原材料存货
        List<SrStorehouseProduct> storehouseProducts = srStorehouseProductDao.selectList(new LambdaQueryWrapper<SrStorehouseProduct>()
                .eq(SrStorehouseProduct::getStorehouseId, endProductProduceDTO.getStorehouseId())
                .in(SrStorehouseProduct::getProductId, produceList.stream().map(SrProductMap::getOriginalProductId).collect(Collectors.toList())));
        //判断仓库是否有所需要的原材料
        if (storehouseProducts.size() != produceList.size()) {
            return new ResponseResult(ProductCode.NO_GOODS);
        }
        //获取原材料id与原材料的对应关系
        Map<Long, SrStorehouseProduct> storehouseProductMap = storehouseProducts.stream().collect(Collectors.toMap(SrStorehouseProduct::getProductId, Function.identity()));
        //判断仓库是否有足够的原材料
        for (SrProductMap srProductMap : produceList) {
            //该原材料需要消耗的数量
            int num = srProductMap.getProductNum() * endProductProduceDTO.getEndProductNum();
            //查询仓库是否有足够 的该原材料
            SrStorehouseProduct srStorehouseProduct = storehouseProductMap.get(srProductMap.getOriginalProductId());
            if (srStorehouseProduct.getProductNum() < num) {
                ExceptionCast.cast(ProductCode.NO_GOODS);
            }
            //减少库存数量
            storeHouseService.reduceProduct(srProductMap.getOriginalProductId(), num, endProductProduceDTO.getStorehouseId());
        }
        //记录生产日志
        SrOutstorehouseLog srOutstorehouseLog = new SrOutstorehouseLog();
        srOutstorehouseLog.setStorehouseId(endProductProduceDTO.getStorehouseId());
        srOutstorehouseLog.setOutStatus(OutStatusEnum.STORED);
        srOutstorehouseLog.setUserId(userId);
        srOutstorehouseLog.setEndProductId(endProductProduceDTO.getEndProductId());
        srOutstorehouseLog.setProductNum(endProductProduceDTO.getEndProductNum());
        srOutstorehouseLog.setCreateTime(System.currentTimeMillis());
        srOutstorehouseLog.setUpdateTime(System.currentTimeMillis());
        srOutstorehouseLogDao.insert(srOutstorehouseLog);
        return ResponseResult.success();
    }

    /**
     * 更改成品与原材料对应关系的数量
     *
     * @param endProductMapDTO map
     * @return
     */
    public ResponseResult updateEndProductMap(EndProductMapDTO endProductMapDTO) {
        int update = srProductMapDao.update(null, new LambdaUpdateWrapper<SrProductMap>()
                .set(SrProductMap::getProductNum, endProductMapDTO.getProductNum())
                .eq(SrProductMap::getId, endProductMapDTO.getProductMapId()));
        return update==1?ResponseResult.success():ResponseResult.fail();
    }
}
