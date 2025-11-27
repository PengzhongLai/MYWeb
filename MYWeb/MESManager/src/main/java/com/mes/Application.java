package com.mes;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//启动类
@MapperScan(basePackages = {"com.mes.mapper"}) //扫描注册 MyBatis 的 Mapper 接口
@SpringBootApplication  //启动 Spring Boot 应用程序
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
