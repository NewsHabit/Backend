package org.newshabit.app.auth.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "org.newshabit.app.auth.infrastructure.adapter.outbound.persistence")
@EnableJpaRepositories(basePackages = "org.newshabit.app.auth.infrastructure.adapter.outbound.persistence")
@ComponentScan(basePackages = {"org.newshabit.app.common", "org.newshabit.app.auth", "org.newshabit.common.auth"})
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}
}
