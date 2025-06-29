package org.newshabit.app.auth.application.port.output;

import org.newshabit.app.auth.domain.model.CustomUserDetail;
import org.newshabit.app.auth.common.exception.AccessTokenException;

public interface TokenCheckerOutputPort {
	CustomUserDetail getUserDetail(String accessToken) throws AccessTokenException;
}
