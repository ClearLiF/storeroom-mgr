package com.wxy.model.response.log;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wxy.model.SrAdmin;
import com.wxy.model.SrOriginalProduct;
import com.wxy.model.SrStorehouse;
import com.wxy.model.enums.RefundStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * sr_refund_log
 * @author 
 */
@Data
public class SrRefundLogVO implements Serializable {
    /**
     * id 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="id 主键")
    private Long id;

    /**
     * 库房id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="库房id")
    private Long storehouseId;

    /**
     * 产品id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="产品id")
    private Long productId;

    /**
     * 退货数量
     */
    @ApiModelProperty(value="退货数量")
    private Integer productNum;

    /**
     * 退货状态(0已退货 1未退货物 2取消退货)
     */
    @ApiModelProperty(value="退货状态(0已退货 1未退货物 2取消退货)")
    private RefundStatusEnum refundStatus;

    /**
     * 退货原因
     */
    @ApiModelProperty(value="退货原因")
    private String refundReason;

    /**
     * 操作人id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="操作人id")
    private Long userId;

    /**
     * 退货时间
     */
    @ApiModelProperty(value="退货时间")
    private Long createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Long updateTime;



    @ApiModelProperty(value="仓库详情")
    private SrStorehouse storehouseInfo;

    @ApiModelProperty(value="管理员详情")
    private SrAdmin adminInfo;

    @ApiModelProperty(value="产品详情")
    private SrOriginalProduct productInfo;

    private static final long serialVersionUID = 1L;
}