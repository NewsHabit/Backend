package org.newshabit.app.news.infrastructure.adapter.inbound.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.common.response.CommonResponse;
import org.newshabit.app.news.application.port.input.BookmarkUseCase;
import org.newshabit.app.news.application.port.input.NewsReadLogUseCase;
import org.newshabit.app.news.application.port.input.RefinedNewsUseCase;
import org.newshabit.app.news.domain.model.RefinedNews;
import org.newshabit.app.auth.domain.model.CustomUserDetail;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.BookmarkRequestDto;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.NewsReadLogRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {
	private final RefinedNewsUseCase refinedNewsUseCase;
	private final NewsReadLogUseCase newsReadLogUseCase;
	private final BookmarkUseCase bookmarkUseCase;

	@GetMapping("/v2/member/today-news")
	public ResponseEntity<CommonResponse<List<RefinedNews>>> getTodayNews(
		@AuthenticationPrincipal CustomUserDetail userDetail
	) {
		int userId = userDetail.getUserId();

		List<RefinedNews> todayNews = refinedNewsUseCase.getTodayNews(userId);

		CommonResponse<List<RefinedNews>> commonResponse = CommonResponse.success(todayNews);

		return ResponseEntity.ok(commonResponse);
	}

	@PostMapping("/v2/guest/read-articles")
	public ResponseEntity<CommonResponse<Void>> updateNewsReadHistory(
		@AuthenticationPrincipal CustomUserDetail userDetail,
		@RequestBody NewsReadLogRequestDto requestDto
	) {
		Integer userId = userDetail.getUserId();

		newsReadLogUseCase.updateNewsReadLog(userId, requestDto.newsId());

		return ResponseEntity.ok(CommonResponse.success());
	}

	@GetMapping("/v2/member/bookmarks")
	public ResponseEntity<CommonResponse<List<RefinedNews>>> getBookmarks(
			@AuthenticationPrincipal CustomUserDetail userDetail
	) {
		Integer userId = userDetail.getUserId();

		List<RefinedNews> bookmarkedNews = bookmarkUseCase.getBookmarkedNews(userId);

		return ResponseEntity.ok(CommonResponse.success(bookmarkedNews));
	}

	@PostMapping("/v2/member/bookmarks")
	public ResponseEntity<CommonResponse<Void>> addBookmarks(
			@AuthenticationPrincipal CustomUserDetail userDetail,
			@RequestBody BookmarkRequestDto requestDto
	) {
		Integer userId = userDetail.getUserId();

		bookmarkUseCase.addBookmark(userId, requestDto.newsId());

		return ResponseEntity.ok(CommonResponse.success());
	}

	@GetMapping("/v2/guest/trending")
	public ResponseEntity<CommonResponse<List<RefinedNews>>> getTrendingNews(
			@RequestParam(name = "page", required = false, defaultValue = "0") int page
	) {
		List<RefinedNews> trendingNews = refinedNewsUseCase.getTrendingNews(page);

		return ResponseEntity.ok(CommonResponse.success(trendingNews));
	}
}
