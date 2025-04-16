package com.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class MallBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallBackendApplication.class, args);
        log.info("项目启动成功");
        log.info("http://localhost:8080/api/swagger-ui.html");
    }
}
