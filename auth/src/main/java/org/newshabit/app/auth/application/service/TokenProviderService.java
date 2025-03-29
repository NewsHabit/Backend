package org.newshabit.app.auth.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.auth.application.port.TokenProviderOutputPort;
import org.newshabit.app.auth.application.port.TokenProviderUseCase;
import org.newshabit.app.auth.domain.dto.ReissueAccessTokenResponse;
import org.newshabit.app.common.domain.enums.UserRole;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenProviderService implements TokenProviderUseCase {
	private final TokenProviderOutputPort tokenProviderOutputPort;
	@Override
	public String createAccessToken(String socialId, List<UserRole> roles) {
		return tokenProviderOutputPort.createAccessToken(socialId, roles);
	}

	@Override
	public String createRefreshToken(String socialId, List<UserRole> roles) {
		return tokenProviderOutputPort.createRefreshToken(socialId, roles);
	}

	@Override
	public ReissueAccessTokenResponse reissueAccessToken(String refreshToken) {
		return new ReissueAccessTokenResponse(
			tokenProviderOutputPort.reissueAccessToken(refreshToken),
			refreshToken
		);
	}
}
