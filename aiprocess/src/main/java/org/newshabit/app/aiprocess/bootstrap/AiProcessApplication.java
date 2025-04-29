package org.newshabit.app.aiprocess.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients(basePackages = "org.newshabit.app.aiprocess")
@ComponentScan(basePackages = {"org.newshabit.app.common", "org.newshabit.app.aiprocess"})
public class AiProcessApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiProcessApplication.class, args);
    }
}

