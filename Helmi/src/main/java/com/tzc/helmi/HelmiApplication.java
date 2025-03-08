package com.tzc.helmi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.tzc.helmi.mapper")
public class HelmiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelmiApplication.class, args);
    }

}
