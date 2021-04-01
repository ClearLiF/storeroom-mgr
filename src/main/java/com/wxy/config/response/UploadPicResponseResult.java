package com.wxy.config.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author wxy
 */
@Data
@ToString
@NoArgsConstructor
public class UploadPicResponseResult extends ResponseResult{

    private String picUrl;

    public UploadPicResponseResult(ResultCode resultCode, String pic){
        super(resultCode);
        this.picUrl = pic;
    }
}
