package org.newshabit.app.pubsub.infrastructure.adapter.outbound;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.pubsub.application.port.AiOutputPort;
import org.newshabit.app.pubsub.domain.dto.AiProcessedNews;
import org.newshabit.app.pubsub.domain.dto.GeminiRequest;
import org.newshabit.app.pubsub.domain.dto.GeminiResponse;
import org.newshabit.app.common.domain.model.CrawledNews;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AiAdapter implements AiOutputPort {
	@Value("${app.ai.url}")
	private String API_URL;
	@Value("${app.ai.prompt}")
	private String PROMPT;
	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public Optional<AiProcessedNews> aiProcessNews(CrawledNews crawledNews) throws IOException, InterruptedException {
		GeminiRequest request = createGeminiRequest(crawledNews);

		String jsonBody = objectMapper.writeValueAsString(request);

		HttpClient client = HttpClient.newHttpClient();

		HttpRequest httpRequest = HttpRequest.newBuilder()
			.uri(URI.create(API_URL))
			.header("Content-Type", "application/json")
			.POST(HttpRequest.BodyPublishers.ofString(jsonBody))
			.build();

		GeminiResponse response = objectMapper.readValue(
			client.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body(),
			GeminiResponse.class
		);

		if (response.candidates() == null) {
			log.warn("No candidates found for request: {}", response);
			return Optional.empty();
		}

		return Optional.ofNullable(objectMapper.readValue(
			response.candidates().get(0).content().parts().get(0).text(),
			AiProcessedNews.class
		));
	}

	private GeminiRequest createGeminiRequest(CrawledNews crawledNews) {
		return new GeminiRequest(
			List.of(new GeminiRequest.Content(
				List.of(
					new GeminiRequest.Part(PROMPT),
					new GeminiRequest.Part("{"
						+ "\"originTitle\": \"" + crawledNews.getTitle() + "\", "
						+ "\"originContent\": \"" + crawledNews.getContent() + "\""
						+ "}")
				))
			),
			new GeminiRequest.GenerationConfig(
				"application/json",
				Map.of(
					"type", "object",
					"properties", Map.of(
						"title", Map.of("type", "string"),
						"who", Map.of("type", "string"),
						"what", Map.of("type", "string"),
						"when", Map.of("type", "string"),
						"why", Map.of("type", "string"),
						"where", Map.of("type", "string"),
						"how", Map.of("type", "string"),
						"summary", Map.of("type", "string"),
						"keyword", Map.of("type", "string")
					),
					"required", List.of("title", "who", "what", "when", "why", "where", "how", "summary", "keyword")
				)
			)
		);
	}
}
