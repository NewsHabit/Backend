package org.newshabit.app.auth.infrastructure.adapter.inbound.web.dto;

public record TokenCheckResponse(
	boolean isExpired
) { }
