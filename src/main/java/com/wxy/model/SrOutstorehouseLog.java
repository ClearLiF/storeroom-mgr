package com.wxy.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wxy.model.enums.OutStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * sr_outstorehouse_log
 * @author 
 */
@Data
public class SrOutstorehouseLog implements Serializable {
    /**
     * id 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="id 主键")
    private Long id;

    /**
     * 出库仓库id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="出库仓库id")
    private Long storehouseId;

    @ApiModelProperty(value="产品数量")
    private Integer productNum;

    /**
     * 出库状态(0已出库，1未出库 2取消出库)
     */
    @ApiModelProperty(value="出库状态(0已出库，1未出库 2取消出库)")
    private OutStatusEnum outStatus;

    /**
     * 操作员id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="操作员id")
    private Long userId;

    /**
     * 出库要生产的产品id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="出库要生产的产品id")
    private Long endProductId;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Long createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Long updateTime;


    @TableField(exist = false)
    @ApiModelProperty(value="出库仓库信息")
    private SrStorehouse storehouseInfo;

    @TableField(exist = false)
    @ApiModelProperty(value="管理员信息")
    private SrAdmin userInfo;

    @TableField(exist = false)
    @ApiModelProperty(value="成品信息")
    private SrEndProduct endProductInfo;

    private static final long serialVersionUID = 1L;
}