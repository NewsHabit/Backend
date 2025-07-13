package org.newshabit.app.auth.application.port.output;

import org.newshabit.app.auth.domain.model.CustomUserDetail;
import org.newshabit.app.auth.common.exception.AccessTokenException;

public interface TokenCheckerPort {
	CustomUserDetail getUserDetail(String accessToken) throws AccessTokenException;
	boolean isExpired(String token);
}
