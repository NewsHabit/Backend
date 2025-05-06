package org.newshabit.app.news.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "org.newshabit.app.news.domain.entity")
@EnableJpaRepositories(basePackages = "org.newshabit.app.news.infrastructure.repository")
@ComponentScan(basePackages = {"org.newshabit.app.common", "org.newshabit.common.auth", "org.newshabit.app.news"})
public class NewsApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsApplication.class, args);
    }
}

