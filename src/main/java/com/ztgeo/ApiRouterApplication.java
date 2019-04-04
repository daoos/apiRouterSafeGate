package com.ztgeo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@MapperScan("com.ztgeo.mapper")
@SpringBootApplication
public class ApiRouterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiRouterApplication.class, args);
    }

}
