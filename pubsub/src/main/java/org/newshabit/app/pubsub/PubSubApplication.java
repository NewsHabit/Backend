package org.newshabit.app.pubsub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"org.newshabit.app.common", "org.newshabit.app.auth", "org.newshabit.app.pubsub"})
public class PubSubApplication {

    public static void main(String[] args) {
        SpringApplication.run(PubSubApplication.class, args);
    }
}

