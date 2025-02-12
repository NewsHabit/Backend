package org.newshabit.app.crawl.infrastructure.adapter;

import java.util.function.Consumer;
import org.newshabit.app.crawl.application.port.MessageOutputPort;
import org.newshabit.app.crawl.domain.News;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

@Configuration
public class MessageAdapter implements MessageOutputPort {
	@Bean
	public Consumer<Message<News>> input() throws RuntimeException {
		return message -> {
			System.out.println("input: " + message.getPayload().toString());
		};
	}

	@Bean
	public Consumer<Message<News>> dlq() throws RuntimeException {
		return message -> {
			System.out.println("DLQ: " + message.getPayload());
		};
	}
}
