package com.wxy.model.request.endProduct;

import com.baomidou.mybatisplus.annotation.TableField;
import com.wxy.model.SrProductMap;
import com.wxy.model.enums.DeleteMarkEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * sr_end_product
 * @author 
 */
@Data
public class EditEndProductDTO implements Serializable {
    /**
     * 成品id
     */
    @ApiModelProperty(value="成品id")
    private Long id;

    /**
     * 成品名称
     */
    @ApiModelProperty(value="成品名称")
    private String productName;

    /**
     * 成品图片
     */
    @ApiModelProperty(value="成品图片")
    private String productImage;

    /**
     * 成品成本(单位分)
     */
    @ApiModelProperty(value="成品成本(单位分)")
    private Integer productPrice;

    @ApiModelProperty(value="原材料与原料的对应关系")
    private List<EndProductMapDTO> endProductMapList;


    private static final long serialVersionUID = 1L;
}