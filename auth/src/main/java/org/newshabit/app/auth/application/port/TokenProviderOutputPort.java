package org.newshabit.app.auth.application.port;

import java.util.List;
import org.newshabit.app.auth.domain.model.CustomUserDetail;
import org.newshabit.app.auth.infrastructure.exception.AccessTokenException;
import org.newshabit.app.common.domain.enums.UserRole;

public interface TokenProviderOutputPort {
	String createAccessToken(String socialId, List<UserRole> roles);
	String createRefreshToken(String socialId, List<UserRole> roles);
	String reissueAccessToken(String refreshToken) throws AccessTokenException;
	CustomUserDetail getUserDetail(String accessToken) throws AccessTokenException;
}
