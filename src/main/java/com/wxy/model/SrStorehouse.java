package com.wxy.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.wxy.model.enums.SrStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * sr_storehouse
 * @author 
 */
@ApiModel(value="generate.SrStorehouse库房表 ")
@Data
public class SrStorehouse implements Serializable {
    /**
     * id库房id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="id库房id")
    private Long id;

    /**
     * 库房名称
     */
    @ApiModelProperty(value=" 库房名称")
    private String srName;

    /**
     * 库房位置 库房位置
     */
    @ApiModelProperty(value="库房位置 库房位置")
    private String srSite;

    /**
     * 库房大小(单位m)
     */
    @ApiModelProperty(value="库房大小(单位m)")
    private Integer srSize;

    /**
     * 库房剩余大小
     */
    @ApiModelProperty(value = "库房剩余大小")
    private Integer srRemainSize;

    /**
     * 库房管理人员
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty(value="库房管理人员")
    private Long userId;

    @TableField(exist = false)
    private String username;

    /**
     * 库房是否启用(0启用 1不启用)
     */
    @ApiModelProperty(value="库房是否启用(0启用 1不启用)")
    private SrStatusEnum srStatus;

    /**
     * 更新时间
     */
    @ApiModelProperty(value="更新时间")
    private Long createTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value="创建时间")
    private Long updateTime;

    private static final long serialVersionUID = 1L;
}