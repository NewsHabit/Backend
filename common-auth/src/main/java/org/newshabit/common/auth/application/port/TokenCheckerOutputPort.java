package org.newshabit.common.auth.application.port;

import org.newshabit.common.auth.domain.model.CustomUserDetail;
import org.newshabit.common.auth.infrastructure.exception.AccessTokenException;

public interface TokenCheckerOutputPort {
	CustomUserDetail getUserDetail(String accessToken) throws AccessTokenException;
}
