package com.wxy.model.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

/**
 * @author : wxy
 * @version : V1.0
 * @className : AdminRole
 * @packageName : com.yinghuo.framework.domain.management.admin.request
 * @description : 出库状态
 * @date : 2020-09-24 14:03
 **/
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OutStatusEnum {
    /*
     *入库信息
     */
    STORED("0", "已出库"),
    CANCEL_STORED("2", "取消出库"),
    UN_STORED("1", "未出库");


    @EnumValue
    private String value;
    private String role;

    OutStatusEnum(String value, String role) {
        this.value = value;
        this.role = role;
    }

    private static final ImmutableMap<String, OutStatusEnum> CACHE;

    static {
        final ImmutableMap.Builder<String, OutStatusEnum> builder = ImmutableMap.builder();
        for (OutStatusEnum userGradeEnum : values()) {
            builder.put(userGradeEnum.value, userGradeEnum);
        }
        CACHE = builder.build();
    }


    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static OutStatusEnum getCache(@JsonProperty("value") String value) {
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

    public static ImmutableMap<String, OutStatusEnum> getCACHE() {
        return CACHE;
    }
}
