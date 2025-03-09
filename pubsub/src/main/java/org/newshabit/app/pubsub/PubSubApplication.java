package org.newshabit.app.pubsub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"org.newshabit.app.common", "org.newshabit.app.pubsub"})
@EntityScan(basePackages = "org.newshabit.app.common.domain.entity")
public class PubSubApplication {

    public static void main(String[] args) {
        SpringApplication.run(PubSubApplication.class, args);
    }
}

