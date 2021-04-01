package com.wxy.controller;


import com.wxy.config.response.UploadPicResponseResult;
import com.wxy.service.UploadPicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@CrossOrigin("*")
@Api(tags = "上传图片")
@RestController
public class UploadPicController {

    @Autowired
    private UploadPicService uploadPicService;

    /**
     * type 1为压缩 0 为不压缩
     * @param multipartFile
     * @return
     */
    @ApiOperation(value = "上传图片")
    @PostMapping("/uploadpic")
    public UploadPicResponseResult upload(@RequestParam("file") MultipartFile multipartFile){
            return uploadPicService.upload(multipartFile);
    }

}
