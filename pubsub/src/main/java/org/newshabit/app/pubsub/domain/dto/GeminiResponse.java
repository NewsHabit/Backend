package org.newshabit.app.pubsub.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeminiResponse(List<Candidate> candidates) {

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record Candidate(Content content, String finishReason, double avgLogProbs) {}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record Content(List<Part> parts, String role) {}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record Part(String text) {}
}
