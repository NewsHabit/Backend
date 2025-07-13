package org.newshabit.app.auth.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.auth.application.port.output.AuthRepoPort;
import org.newshabit.app.auth.application.port.output.TokenCheckerPort;
import org.newshabit.app.auth.domain.model.Auth;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenCheckerService {
	private final AuthRepoPort authRepoPort;
	private final TokenCheckerPort tokenCheckerPort;

	@Scheduled(cron = "0 0 0 * * *")
	void deleteExpiredTokens() {
		List<Integer> expiredTokenIds = authRepoPort.findAll().stream()
			.filter( auth -> tokenCheckerPort.isExpired(auth.getRefreshToken()))
			.map(Auth::getId)
			.toList();
		authRepoPort.deleteExpiredTokens(expiredTokenIds);
	}
}
