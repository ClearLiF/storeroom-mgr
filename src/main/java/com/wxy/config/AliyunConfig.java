package com.wxy.config;

import com.aliyun.oss.OSSClient;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@PropertySource("classpath:aliyun.properties")
//@ConfigurationProperties(prefix = "aliyun")
@Data
public class AliyunConfig {

    //LTAI5t6wzWo6HrjUpuyZu3Hk
    //LvoShjWXFKLCR284NVOeMgu0QtiPbn
    private String endpoint = "http://oss-cn-chengdu.aliyuncs.com";
    private String accessKeyId = "LTAI5t6wzWo6HrjUpuyZu3Hk";
    private String accessKeySecret = "LvoShjWXFKLCR284NVOeMgu0QtiPbn";
    private String bucketName= "storeroomwxy";
    private String urlPrefix= "http://storeroomwxy.oss-cn-chengdu.aliyuncs.com/";

    @Bean
    public OSSClient oSSClient() {
        return new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

}
