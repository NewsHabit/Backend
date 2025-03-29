package org.newshabit.app.user.infrastructure.adapter.inbound;

import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.common.response.CommonResponse;
import org.newshabit.app.user.domain.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/user")
public class UserController {
	@GetMapping("/info")
	public ResponseEntity<CommonResponse<Object>> getProfile(@AuthenticationPrincipal UserEntity user) {
		log.info("GOOD!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		return ResponseEntity.ok(CommonResponse.success());
	}
}
