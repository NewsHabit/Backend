package org.newshabit.app.auth.domain.dto;

public record ServerTokenPublishRequest(
	long tokenValidityInMilliseconds
) {}
