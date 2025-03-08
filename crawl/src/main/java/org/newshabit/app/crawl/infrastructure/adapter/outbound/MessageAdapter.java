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
	public void publishMessage(T message, String targetTopic) {
		boolean sent = streamBridge.send(targetTopic, MessageBuilder.withPayload(message).build());
		if (!sent) {
			log.error("message publish to {} failed: {}", targetTopic, message);
		}
	}

	@Override
	public void publishMessages(List<T> messages, String targetTopic) {
		messages.forEach(message -> {
				boolean sent = streamBridge.send(targetTopic, MessageBuilder.withPayload(message).build());
				if (!sent) {
					log.error("message publish to {} failed: {}", targetTopic, message);
				}
		});
	}
}
