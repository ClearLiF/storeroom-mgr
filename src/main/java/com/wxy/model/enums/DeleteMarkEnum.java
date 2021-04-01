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
 * @description : 是否删除枚举类
 * @date : 2020-09-24 14:03
 **/
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum DeleteMarkEnum {
    /*
     *是否删除
     */
    NO_DELETE("0", "未删除"),
    IS_DELETE("1", "已删除");


    @EnumValue
    private String value;
    private String role;

    DeleteMarkEnum(String value, String role) {
        this.value = value;
        this.role = role;
    }

    private static final ImmutableMap<String, DeleteMarkEnum> CACHE;

    static {
        final ImmutableMap.Builder<String, DeleteMarkEnum> builder = ImmutableMap.builder();
        for (DeleteMarkEnum userGradeEnum : values()) {
            builder.put(userGradeEnum.value, userGradeEnum);
        }
        CACHE = builder.build();
    }


    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static DeleteMarkEnum getCache(@JsonProperty("value") String value) {
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

    public static ImmutableMap<String, DeleteMarkEnum> getCACHE() {
        return CACHE;
    }
}
