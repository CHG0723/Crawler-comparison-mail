package com.wind;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = { "com.wind.mapper"}, sqlSessionFactoryRef = "sqlSessionFactory")
public class WebDataApplication {

    public static void main(String[] args) {

        SpringApplication.run(WebDataApplication.class, args);
    }

}
