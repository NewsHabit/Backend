package org.newshabit.app.aiProcessor.infrastructure.adapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.aiProcessor.application.port.AiOutputPort;
import org.newshabit.app.aiProcessor.domain.dto.GeminiRequest;
import org.newshabit.app.aiProcessor.domain.dto.GeminiResponse;
import org.newshabit.app.aiProcessor.domain.RefinedNews;
import org.newshabit.app.common.domain.entity.CrawledNews;
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
	public RefinedNews aiProcessNews(CrawledNews crawledNews) throws IOException, InterruptedException {
		GeminiRequest request = new GeminiRequest(
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

		return objectMapper.readValue(
			response.candidates().get(0).content().parts().get(0).text(),
			RefinedNews.class
		);
	}
}
