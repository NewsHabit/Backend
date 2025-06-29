package org.newshabit.app.news.infrastructure.adapter.inbound.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.common.response.CommonResponse;
import org.newshabit.app.news.application.port.input.RefinedNewsUseCase;
import org.newshabit.app.news.domain.model.RefinedNews;
import org.newshabit.app.auth.domain.model.CustomUserDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {
	private final RefinedNewsUseCase refinedNewsUseCase;

	@GetMapping("/v2/member/today-news")
	public ResponseEntity<CommonResponse<List<RefinedNews>>> getTodayNews(@AuthenticationPrincipal CustomUserDetail userDetail) {
		int userId = userDetail.getUserId();

		List<RefinedNews> todayNews = refinedNewsUseCase.getTodayNews(userId);

		CommonResponse<List<RefinedNews>> commonResponse = CommonResponse.success(todayNews);

		return ResponseEntity.ok(commonResponse);
	}
}
