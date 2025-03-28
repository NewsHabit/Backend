package org.newshabit.app.crawl.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"org.newshabit.app.common", "org.newshabit.app.crawl", "org.newshabit.app.auth"})
public class CrawlApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrawlApplication.class, args);
    }
}

