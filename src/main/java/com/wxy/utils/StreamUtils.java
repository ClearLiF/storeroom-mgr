package com.wxy.utils;

import com.google.common.collect.Lists;
import com.wxy.config.response.CommonCode;
import com.wxy.config.response.QueryResponseResult;
import com.wxy.config.response.QueryResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : wxy
 * @version : V1.0
 * @className : StreamUtils
 * @packageName : com.yinghuo.framework.utils
 * @description : 流处理类
 * @date : 2020-10-24 13:19
 **/
@Slf4j
public class StreamUtils {

    /**
     * 将pojo类转换为vo
     *
     * @param list 源数据list
     * @param cls  目标数据class
     * @return java.util.List<V>
     * @author wxy
     * @date 2020/10/24 13:30
     */
    public static <T, V> List<V> getPojoToVo(List<T> list, Class<V> cls) {
        if (CollectionUtil.isEmpty(list)) {
            return new ArrayList<>();
        }
        return list.stream().map(t -> {
            V v = null;
            try {
                v = cls.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                log.error("初始化vo对象出错");
            }
            //值拷贝
            BeanUtils.copyProperties(t, v);
            return v;
        }).collect(Collectors.toList());
    }

    /**
     * 将pojo类转换为vo
     *
     * @param list 源数据list
     * @param cls  目标数据class
     * @return java.util.List<V>
     * @author wxy
     * @date 2020/10/24 13:30
     */
    public static <T, V> QueryResponseResult<V> getPojoToVoToResponse(List<T> list, Class<V> cls, long total) {
        List<V> listResult = getPojoToVo(list, cls);
        QueryResult<V> result = new QueryResult<>();
        result.setList(listResult);
        result.setTotal(total);
        return new QueryResponseResult<>(CommonCode.SUCCESS, result);
    }

    /**
     * 封装返回对象
     *
     * @param list list集合
     * @param total 总数
     * @return com.yinghuo.framework.response.QueryResponseResult<T>
     * @author wxy
     * @date 2021/3/16 18:11
     */
    public static <T> QueryResponseResult<T> getSimpleResponse(List<T> list, long total) {
        QueryResult<T> result = new QueryResult<>();
        if (CollectionUtil.isEmpty(list)){
            list = Lists.newArrayList();
        }
        result.setList(list);
        result.setTotal(total);
        return new QueryResponseResult<>(CommonCode.SUCCESS, result);
    }

}
