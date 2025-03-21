package org.newshabit.app.crawl.application.port;

import java.util.List;

public interface MessageOutputPort<T> {
	void publishMessage(T messages, String targetTopic);
	void publishMessages(List<T> messages, String targetTopic);
}
