package com.wxy.model.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * sr_storehouse
 * @author 
 */
@ApiModel(value="库房表 ")
@Data
public class NewStorehouseDTO implements Serializable {


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

//    /**
//     * 库房管理人员
//     */
//    @ApiModelProperty(value="库房管理人员")
//    private Long userId;

//    /**
//     * 库房是否启用(0启用 1不启用)
//     */
//    @ApiModelProperty(value="库房是否启用(0启用 1不启用)")
//    private String srStatus;

//    /**
//     * 更新时间
//     */
//    @ApiModelProperty(value="更新时间")
//    private Long createTime;
//
//    /**
//     * 创建时间
//     */
//    @ApiModelProperty(value="创建时间")
//    private Long updateTime;

    private static final long serialVersionUID = 1L;
}