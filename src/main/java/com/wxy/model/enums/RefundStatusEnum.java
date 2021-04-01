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
 * @description : 入库状态
 * @date : 2020-09-24 14:03
 **/
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RefundStatusEnum {
    /*
     *入库信息
     */
    STORED("0", "已退货"),
    CANCEL_STORED("2", "取消退货"),
    UN_STORED("1", "退货中");


    @EnumValue
    private String value;
    private String role;

    RefundStatusEnum(String value, String role) {
        this.value = value;
        this.role = role;
    }

    private static final ImmutableMap<String, RefundStatusEnum> CACHE;

    static {
        final ImmutableMap.Builder<String, RefundStatusEnum> builder = ImmutableMap.builder();
        for (RefundStatusEnum userGradeEnum : values()) {
            builder.put(userGradeEnum.value, userGradeEnum);
        }
        CACHE = builder.build();
    }


    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static RefundStatusEnum getCache(@JsonProperty("value") String value) {
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

    public static ImmutableMap<String, RefundStatusEnum> getCACHE() {
        return CACHE;
    }
}
