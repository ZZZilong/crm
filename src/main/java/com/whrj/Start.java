package com.whrj;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.whrj.dao")
public class Start {

    public static void main(String[] args) {
        SpringApplication.run(Start.class);
    }
}
