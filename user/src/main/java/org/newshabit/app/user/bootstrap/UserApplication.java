package org.newshabit.app.user.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "org.newshabit.app.user.domain.entity")
@EnableJpaRepositories(basePackages = "org.newshabit.app.user.infrastructure.repository")
@ComponentScan(basePackages = {"org.newshabit.app.common", "org.newshabit.app.user", "org.newshabit.app.auth"})
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }
}

