package org.newshabit.app.user.infrastructure.adapter.inbound;

import org.newshabit.app.user.domain.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
public class UserController {
	@GetMapping("/")
	public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserEntity user) {

		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
}
