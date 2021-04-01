package com.wxy;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 主程序
 *
 * @author wxz
 */
@SpringBootApplication(scanBasePackages = {"com.wxy"})
@MapperScan(basePackages = {"com.wxy.dao","generator"})
public class ManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagementApplication.class, args);
    }

}
