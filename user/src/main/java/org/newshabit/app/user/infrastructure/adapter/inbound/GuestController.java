package org.newshabit.app.user.infrastructure.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.newshabit.app.common.response.CommonResponse;
import org.newshabit.app.user.domain.dto.LoginRequest;
import org.newshabit.app.user.domain.dto.LoginResponse;
import org.newshabit.app.user.domain.dto.RegisterRequest;
import org.newshabit.app.user.domain.dto.RegisterResponse;
import org.newshabit.app.user.application.port.GuestUseCase;
import org.newshabit.app.user.exception.DuplicatedException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/guest")
public class GuestController {
	private final GuestUseCase guestUseCase;

	@GetMapping("/login")
	public ResponseEntity<CommonResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) throws NotFoundException {

		LoginResponse loginResponse = guestUseCase.login(loginRequest);

		return ResponseEntity.ok(CommonResponse.success(loginResponse));
	}

	@GetMapping("/register")
	public ResponseEntity<CommonResponse<RegisterResponse>> register(@RequestBody RegisterRequest registerRequest) throws DuplicatedException {

		guestUseCase.register(registerRequest);

		return ResponseEntity.ok(CommonResponse.success());
	}
}
