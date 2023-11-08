package com.psl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.psl.mapper")
@SpringBootApplication
public class SmallGreenBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmallGreenBookApplication.class, args);
    }

}
