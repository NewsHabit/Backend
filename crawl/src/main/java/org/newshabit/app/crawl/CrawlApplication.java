package org.newshabit.app.crawl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"org.newshabit.app.common", "org.newshabit.app.crawl"})
@EntityScan(basePackages = "org.newshabit.app.common.domain.entity")
@EnableJpaRepositories(basePackages = "org.newshabit.app.common.infrastructure.repository")
public class CrawlApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrawlApplication.class, args);
    }
}

