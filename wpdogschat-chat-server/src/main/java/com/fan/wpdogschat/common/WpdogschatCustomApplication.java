package com.fan.wpdogschat.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.fan.wpdogschat"})
public class WpdogschatCustomApplication {
    public static void main(String[] args) {
        SpringApplication.run(WpdogschatCustomApplication.class);
    }
}
