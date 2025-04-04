package org.newshabit.app.crawl.application.port;

import java.util.List;

public interface MessageOutputPort<T> {
	void publishMessage(T messages, String topic);
	void publishMessages(List<T> messages, String topic);
}
