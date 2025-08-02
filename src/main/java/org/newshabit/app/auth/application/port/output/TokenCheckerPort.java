package org.newshabit.app.auth.application.port.output;

import java.util.Optional;
import org.newshabit.app.auth.domain.model.CustomUserDetail;
import org.newshabit.app.auth.common.exception.AccessTokenException;

public interface TokenCheckerPort {
	Optional<CustomUserDetail> getUserDetail(String accessToken) throws AccessTokenException;
	boolean isValid(String token);
}
