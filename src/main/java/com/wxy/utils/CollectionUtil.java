package com.wxy.utils;

import com.google.common.collect.Lists;
import com.wxy.config.response.CommonCode;
import com.wxy.config.response.QueryResponseResult;
import com.wxy.config.response.QueryResult;

import java.util.Collection;
import java.util.List;

/**
 * @author : wxy
 * @version : V1.0
 * @className : CollectionUtil
 * @packageName : com.yinghuo.framework.utils
 * @description : 集合工具类
 * @date : 2020-09-20 12:50
 **/
public class CollectionUtil {
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.size() == 0;
    }

    public static boolean isNotEmpty(Collection<?> collection) {

        return collection != null && collection.size() != 0;
    }



}
