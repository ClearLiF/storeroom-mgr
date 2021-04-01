package com.wxy.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author : wxy
 * @version : V1.0
 * @className : FastJsonConfig
 * @packageName : com.yinghuo.api.config
 * @description : mybatis-plus的 通用枚举配置要使用
 * @date : 2020-10-16 23:40
 **/
@Component
public class FastJsonConfigBean {
    @Bean
    FastJsonConfig getFast(){
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteEnumUsingToString);
        return config;
    }
}
