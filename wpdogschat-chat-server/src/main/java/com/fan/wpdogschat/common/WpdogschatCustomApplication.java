package com.fan.wpdogschat.common;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.fan.wpdogschat"})
@MapperScan({"com.fan.wpdogschat.common.**.mapper"})
public class WpdogschatCustomApplication {
    public static void main(String[] args) {
        SpringApplication.run(WpdogschatCustomApplication.class);
    }
}
