package org.newshabit.app.pubsub.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record GeminiRequest(
	List<Content> contents,
	GenerationConfig generationConfig
) {
	public record Content(List<Part> parts) {}

	public record Part(String text) {}

	public record GenerationConfig(
		@JsonProperty("response_mime_type") String responseMimeType,
		@JsonProperty("response_schema") Map<String, Object> responseSchema
	) {}
}
