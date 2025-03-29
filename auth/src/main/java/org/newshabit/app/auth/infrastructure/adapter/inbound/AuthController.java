package org.newshabit.app.auth.infrastructure.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.newshabit.app.auth.application.port.TokenProviderUseCase;
import org.newshabit.app.auth.domain.dto.ReissueAccessTokenRequest;
import org.newshabit.app.auth.domain.dto.ReissueAccessTokenResponse;
import org.newshabit.app.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/guest")
@RequiredArgsConstructor
public class AuthController {
	private final TokenProviderUseCase tokenProviderUseCase;

	@GetMapping("/auth/refresh")
	public ResponseEntity<CommonResponse<ReissueAccessTokenResponse>> reissueAccessToken(@RequestBody ReissueAccessTokenRequest request) {

		ReissueAccessTokenResponse response = tokenProviderUseCase.reissueAccessToken(request.refreshToken());

		return ResponseEntity.ok(CommonResponse.success(response));
	}
}
