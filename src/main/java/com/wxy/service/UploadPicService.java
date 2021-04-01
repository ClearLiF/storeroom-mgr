package com.wxy.service;

import cn.hutool.core.date.DateTime;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.wxy.config.AliyunConfig;
import com.wxy.config.response.CommonCode;
import com.wxy.config.response.UploadPicResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
@Slf4j
public class UploadPicService {


    // 允许上传的格式
    private static final String[] IMAGE_TYPE = new String[]{".bmp", ".jpg",
            ".jpeg", ".gif", ".png"};

    @Autowired
    private OSSClient ossClient;
    @Autowired
    private AliyunConfig aliyunConfig;

    /**
     * 上传图片
     *
     * @param uploadFile
     * @return
     */
    public UploadPicResponseResult upload(MultipartFile uploadFile) {
        //图片做校验，对后缀名
        boolean isLegal = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(uploadFile.getOriginalFilename(), type)) {
                isLegal = true;
                break;
            }
        }
        //失败
        if (!isLegal) {
            return new UploadPicResponseResult(CommonCode.FAIL, null);
        }

        // 文件新路径
        String fileName = uploadFile.getOriginalFilename();
        String filePath = getFilePath(fileName);
        // 创建上传文件的元信息，可以通过文件元信息设置HTTP header。
        ObjectMetadata meta = new ObjectMetadata();
        meta.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
        // 设置内容被下载时的编码格式。
        meta.setContentEncoding("utf-8");


        // 上传到阿里云
        try {

            ossClient.putObject(aliyunConfig.getBucketName(), filePath, new
                    ByteArrayInputStream(uploadFile.getBytes()), meta);
        } catch (Exception e) {
            e.printStackTrace();
            //上传失败
            return new UploadPicResponseResult(CommonCode.FAIL, null);
        }

        // 上传成功
        String picUrl = aliyunConfig.getUrlPrefix() + filePath;
        return new UploadPicResponseResult(CommonCode.SUCCESS, picUrl);
    }




    /**
     * 用来生成在阿里云上图片的保存路径
     *
     * @param sourceFileName
     * @return
     */
    private String getFilePath(String sourceFileName) {
        DateTime dateTime = new DateTime();
        return "images/" + dateTime.toString("yyyy")
                + "/" + dateTime.toString("MM") + "/"
                + dateTime.toString("dd") + "/" + System.currentTimeMillis() +
                "." +
                StringUtils.substringAfterLast(sourceFileName, ".");
    }

    /**
     * 用来设置文件上传的格式
     *
     * @param FilenameExtension
     * @return
     */
    public String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpg";
    }

}
