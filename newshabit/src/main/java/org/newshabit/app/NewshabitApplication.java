package org.newshabit.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EntityScan(basePackages = {
    "org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity",
    "org.newshabit.app.auth.infrastructure.adapter.outbound.persistence.entity",
    "org.newshabit.app.user.infrastructure.adapter.outbound.persistence.entity"
})
@EnableJpaRepositories(basePackages = {
    "org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository",
    "org.newshabit.app.auth.infrastructure.adapter.outbound.persistence.repository",
    "org.newshabit.app.user.infrastructure.adapter.outbound.persistence.repository"
})
@ComponentScan(basePackages = {
    "org.newshabit.app.aiprocess",
    "org.newshabit.app.common",
    "org.newshabit.common.auth",
    "org.newshabit.app.news",
    "org.newshabit.app.auth",
    "org.newshabit.app.crawl",
    "org.newshabit.app.user"
})
public class NewshabitApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewshabitApplication.class, args);
    }
}

