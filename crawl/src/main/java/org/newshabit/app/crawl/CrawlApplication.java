package org.newshabit.app.crawl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"org.newshabit.app.common", "org.newshabit.app.crawl"})

public class CrawlApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrawlApplication.class, args);
    }
}

