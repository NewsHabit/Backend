package org.newshabit.app.auth.application.port;

import java.util.List;
import org.newshabit.app.auth.domain.dto.ReissueAccessTokenResponse;
import org.newshabit.app.common.domain.enums.UserRole;

public interface TokenProviderUseCase {
	String createAccessToken(String socialId, List<UserRole> roles);
	String createRefreshToken(String socialId, List<UserRole> roles);
	ReissueAccessTokenResponse reissueAccessToken(String refreshToken);
}
