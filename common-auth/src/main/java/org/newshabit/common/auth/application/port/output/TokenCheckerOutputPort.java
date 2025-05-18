package org.newshabit.common.auth.application.port.output;

import org.newshabit.common.auth.domain.model.CustomUserDetail;
import org.newshabit.common.auth.common.exception.AccessTokenException;

public interface TokenCheckerOutputPort {
	CustomUserDetail getUserDetail(String accessToken) throws AccessTokenException;
}
