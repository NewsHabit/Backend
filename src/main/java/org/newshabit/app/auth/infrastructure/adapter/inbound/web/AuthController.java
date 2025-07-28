package org.newshabit.app.auth.infrastructure.adapter.inbound.web;

import lombok.RequiredArgsConstructor;
import org.newshabit.app.auth.application.port.input.TokenProviderUseCase;
import org.newshabit.app.auth.infrastructure.adapter.inbound.web.dto.ReissueAccessTokenRequest;
import org.newshabit.app.auth.infrastructure.adapter.inbound.web.dto.ReissueAccessTokenResponse;
import org.newshabit.app.auth.infrastructure.adapter.inbound.web.dto.TokenCheckRequest;
import org.newshabit.app.auth.infrastructure.adapter.inbound.web.dto.TokenCheckResponse;
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

	@PostMapping("/v2/guest/refresh")
	public ResponseEntity<CommonResponse<ReissueAccessTokenResponse>> reissueAccessToken(@RequestBody ReissueAccessTokenRequest request) {

		ReissueAccessTokenResponse response = tokenProviderUseCase.reissueAccessToken(
			request.refreshToken()
		);

		return ResponseEntity.ok(CommonResponse.success(response));
	}

	@GetMapping("/v2/guest/token/check")
	public ResponseEntity<CommonResponse<TokenCheckResponse>> checkToken(@RequestBody TokenCheckRequest request) {
		boolean isValid = tokenProviderUseCase.checkAccessToken(request.accessToken());

		TokenCheckResponse response = new TokenCheckResponse(isValid);

		return ResponseEntity.ok(CommonResponse.success(response));
	}
}
