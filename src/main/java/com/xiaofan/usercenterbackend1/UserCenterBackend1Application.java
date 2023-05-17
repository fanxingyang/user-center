package com.xiaofan.usercenterbackend1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.xiaofan.usercenterbackend1.mapper")
public class UserCenterBackend1Application {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterBackend1Application.class, args);
    }

}
