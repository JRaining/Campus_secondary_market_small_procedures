package com.xiaojian.pick;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling   // 开启定时任务
@SpringBootApplication
@MapperScan("com.xiaojian.pick.mapper")
public class PickApplication {

    public static void main(String[] args) {
        SpringApplication.run(PickApplication.class, args);
    }

}
