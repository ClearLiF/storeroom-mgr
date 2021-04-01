package com.wxy.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

/**
 * @author : CLEAR Li
 * @version : V1.0
 * @className : AdminRole
 * @packageName : com.yinghuo.framework.domain.management.admin.request
 * @description : 是否启用仓库枚举类
 * @date : 2020-09-24 14:03
 **/
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SrStatusEnum {
    /*
     *是否启用该仓库
     */
    USE("0", "启用"),
    UN_USE("1", "不启用");


    @EnumValue
    private String value;
    private String role;

    SrStatusEnum(String value, String role) {
        this.value = value;
        this.role = role;
    }

    private static final ImmutableMap<String, SrStatusEnum> CACHE;

    static {
        final ImmutableMap.Builder<String, SrStatusEnum> builder = ImmutableMap.builder();
        for (SrStatusEnum userGradeEnum : values()) {
            builder.put(userGradeEnum.value, userGradeEnum);
        }
        CACHE = builder.build();
    }


    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static SrStatusEnum getCache(@JsonProperty("value") String value) {
        System.out.println(value);
        return CACHE.get(value);
    }
    @JsonProperty
    public String getValue() {
        return value;
    }

    public String getRole() {
        return role;
    }

    public static ImmutableMap<String, SrStatusEnum> getCACHE() {
        return CACHE;
    }
}
