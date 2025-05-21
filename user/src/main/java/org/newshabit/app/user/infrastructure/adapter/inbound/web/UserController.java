package org.newshabit.app.user.infrastructure.adapter.inbound.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.common.response.CommonResponse;
import org.newshabit.app.user.application.port.input.MemberUserCase;
import org.newshabit.app.user.domain.model.MemberSettings;
import org.newshabit.app.user.infrastructure.adapter.inbound.web.dto.LoginRequest;
import org.newshabit.app.user.infrastructure.adapter.inbound.web.dto.LoginResponse;
import org.newshabit.app.user.infrastructure.adapter.inbound.web.dto.RegisterRequest;
import org.newshabit.app.user.infrastructure.adapter.inbound.web.dto.RegisterResponse;
import org.newshabit.app.user.application.port.input.GuestUseCase;
import org.newshabit.app.user.infrastructure.adapter.inbound.web.dto.SettingsResponse;
import org.newshabit.app.user.infrastructure.adapter.inbound.web.dto.mapper.DtoMapper;
import org.newshabit.app.user.common.exception.DuplicatedException;
import org.newshabit.common.auth.domain.model.CustomUserDetail;
import org.newshabit.common.auth.domain.model.Token;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	private final GuestUseCase guestUseCase;
	private final MemberUserCase memberUserCase;
	private final DtoMapper dtoMapper;

	@PostMapping("/v2/guest/login")
	public ResponseEntity<CommonResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) throws NotFoundException {

		Token token = guestUseCase.login(loginRequest.socialId(), loginRequest.deviceId());

		return ResponseEntity.ok(CommonResponse.success(new LoginResponse(token.accessToken, token.refreshToken)));
	}

	@PostMapping("/v2/guest/register")
	public ResponseEntity<CommonResponse<RegisterResponse>> register(@RequestBody RegisterRequest registerRequest) throws DuplicatedException {

		guestUseCase.register(dtoMapper.toDomain(registerRequest));

		return ResponseEntity.ok(CommonResponse.success());
	}

	@GetMapping("/v2/member/settings")
	public ResponseEntity<CommonResponse<Object>> getProfile(@AuthenticationPrincipal CustomUserDetail userDetail) throws NotFoundException {

		MemberSettings memberSettings = memberUserCase.getMemberSettings(userDetail.getUserId());

		return ResponseEntity.ok(CommonResponse.success(dtoMapper.toDto(memberSettings)));
	}
}
