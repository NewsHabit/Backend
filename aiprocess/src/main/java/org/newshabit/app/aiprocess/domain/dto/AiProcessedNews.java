package org.newshabit.app.aiprocess.domain.dto;

public record AiProcessedNews(
	String title,
	String who,
	String when,
	String where,
	String what,
	String why,
	String how,
	String keyword,
	String summary) {}
