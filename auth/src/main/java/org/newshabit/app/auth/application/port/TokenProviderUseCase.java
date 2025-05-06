package org.newshabit.app.auth.application.port;

import java.util.List;
import org.newshabit.app.auth.domain.dto.LoginTokenPublishResponse;
import org.newshabit.app.auth.domain.dto.ReissueAccessTokenResponse;
import org.newshabit.app.common.domain.enums.UserRole;

public interface TokenProviderUseCase {
	LoginTokenPublishResponse createLoginToken(String socialId, String deviceId, int userId, List<UserRole> roles);
	String createCustomServerToken(String socialId, int userId, String deviceId, List<UserRole> roles, long tokenValidityInMilliseconds);
	ReissueAccessTokenResponse reissueAccessToken(String refreshToken);
}
