package com.tzc.helmi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
@MapperScan("com.tzc.helmi.mapper")
public class HelmiApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelmiApplication.class, args);
        System.out.println("Spring Boot 应用启动成功！");
    }
}