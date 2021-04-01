package com.wxy.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : StaticMethodGetBean
 * @packageName : com.yinghuo.management.utils
 * @description : 一般类
 * @date : 2020-12-14 1:05
 **/

@Component
public class StaticMethodGetBean<T> implements ApplicationContextAware {

    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        StaticMethodGetBean.applicationContext = applicationContext;

    }

    public static <T> T  getBean(Class<T> clazz) {
        return applicationContext != null?applicationContext.getBean(clazz):null;
    }

}