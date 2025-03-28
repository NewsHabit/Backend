package org.newshabit.app.user.infrastructure.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.newshabit.app.user.domain.entity.UserEntity;
import org.newshabit.app.user.application.port.GuestUseCase;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/guest")
public class GuestController {
	private final GuestUseCase guestUseCase;

	@GetMapping("/login")
	public ResponseEntity<?> login(@AuthenticationPrincipal UserEntity user) throws NotFoundException {


		guestUseCase.login(user);

		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@GetMapping("/register")
	public ResponseEntity<?> register() {

		return ResponseEntity.status(HttpStatus.OK).build();
	}
}
