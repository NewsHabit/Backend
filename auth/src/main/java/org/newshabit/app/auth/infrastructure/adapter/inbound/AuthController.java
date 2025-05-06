package org.newshabit.app.auth.infrastructure.adapter.inbound;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.auth.application.port.TokenProviderUseCase;
import org.newshabit.app.auth.domain.dto.LoginTokenPublishRequest;
import org.newshabit.app.auth.domain.dto.LoginTokenPublishResponse;
import org.newshabit.app.auth.domain.dto.ReissueAccessTokenRequest;
import org.newshabit.app.auth.domain.dto.ReissueAccessTokenResponse;
import org.newshabit.app.auth.domain.dto.ServerTokenPublishRequest;
import org.newshabit.app.auth.domain.dto.ServerTokenPublishResponse;
import org.newshabit.app.common.domain.enums.UserRole;
import org.newshabit.app.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final TokenProviderUseCase tokenProviderUseCase;

	@PostMapping("/v2/internal/login")
	public ResponseEntity<CommonResponse<LoginTokenPublishResponse>> login(@RequestBody LoginTokenPublishRequest request) {

		LoginTokenPublishResponse response = tokenProviderUseCase.createLoginToken(
			request.socialId(),
			request.deviceId(),
			request.userId(),
			request.roles()
		);

		return ResponseEntity.ok(CommonResponse.success(response));
	}

	@GetMapping("/v2/guest/refresh")
	public ResponseEntity<CommonResponse<ReissueAccessTokenResponse>> reissueAccessToken(@RequestBody ReissueAccessTokenRequest request) {

		ReissueAccessTokenResponse response = tokenProviderUseCase.reissueAccessToken(
			request.refreshToken()
		);

		return ResponseEntity.ok(CommonResponse.success(response));
	}

	@PostMapping("/v2/admin/server-token")
	public ResponseEntity<CommonResponse<ServerTokenPublishResponse>> serverToken(@RequestBody ServerTokenPublishRequest request) {

		ServerTokenPublishResponse response = new ServerTokenPublishResponse(
			tokenProviderUseCase.createCustomServerToken(
				"INTERNAL_SERVER",
				0,
				"SERVER",
				List.of(UserRole.INTERNAL),
				request.tokenValidityInMilliseconds()
			)
		);

		return ResponseEntity.ok(CommonResponse.success(response));
	}
}
