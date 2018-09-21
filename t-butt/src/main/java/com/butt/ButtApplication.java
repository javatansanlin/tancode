package com.butt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.butt.dao")
@EnableSwagger2
@EnableScheduling
@ServletComponentScan
public class ButtApplication {

    public static void main(String[] args) {
        SpringApplication.run(ButtApplication.class, args);
    }

}
