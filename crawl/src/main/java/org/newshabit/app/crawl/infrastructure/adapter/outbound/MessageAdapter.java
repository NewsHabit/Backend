package org.newshabit.app.crawl.infrastructure.adapter.outbound;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.crawl.application.port.MessageOutputPort;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageAdapter<T> implements MessageOutputPort<T> {
	private final StreamBridge streamBridge;

	@Override
	public void publishMessage(T message, String topic) {
		boolean sent = streamBridge.send(topic, MessageBuilder.withPayload(message).build());
		if (!sent) {
			log.error("message publish to {} failed: {}", topic, message);
		}
	}

	@Override
	public void publishMessages(List<T> messages, String topic) {
		messages.forEach(message -> {
			try {
				boolean sent = streamBridge.send(topic, MessageBuilder.withPayload(message).build());
			} catch (Exception e) {
				log.error("message publish to {} failed: {}", topic, message);
				throw new RuntimeException(e);
			}
		});
	}
}
