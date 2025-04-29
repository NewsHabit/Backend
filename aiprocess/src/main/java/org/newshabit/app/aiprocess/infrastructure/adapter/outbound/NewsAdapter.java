package org.newshabit.app.aiprocess.infrastructure.adapter.outbound;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.aiprocess.application.port.NewsOutputPort;
import org.newshabit.app.aiprocess.infrastructure.client.NewsClient;
import org.newshabit.app.common.response.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsAdapter implements NewsOutputPort {
	private final NewsClient newsClient;

	@Override
	public boolean existNews(String url) {
		CommonResponse<Boolean> response = newsClient.existNews(url);

		if (response.getStatus() != HttpStatus.OK.value()) {
			log.error("news service error: {}", response.getStatus());
			return false;
		}

		return response.getData();
	}
}
