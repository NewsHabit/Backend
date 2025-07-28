package org.newshabit.app.auth.application.port.input;

import java.util.List;
import org.newshabit.app.auth.infrastructure.adapter.inbound.web.dto.ReissueAccessTokenResponse;
import org.newshabit.app.auth.domain.model.Token;
import org.newshabit.app.common.domain.enums.UserRole;

public interface TokenProviderUseCase {
	Token createLoginToken(String socialId, String deviceId, int userId, List<UserRole> roles);
	ReissueAccessTokenResponse reissueAccessToken(String refreshToken);
	boolean checkAccessToken(String accessToken);
}
