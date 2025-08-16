package org.newshabit.app.news.infrastructure.adapter.inbound.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.common.response.CommonResponse;
import org.newshabit.app.news.application.port.input.BookmarkUseCase;
import org.newshabit.app.news.application.port.input.NewsReadLogUseCase;
import org.newshabit.app.news.application.port.input.RefinedNewsUseCase;
import org.newshabit.app.news.domain.model.RefinedNews;
import org.newshabit.app.auth.domain.model.CustomUserDetail;
import org.newshabit.app.news.domain.model.NewsSimpleInfo;
import org.newshabit.app.news.domain.model.TodayNewsReadLog;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.BookmarkListResponseDto;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.BookmarkRequestDto;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.BookmarkResponseDto;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.NewsReadLogRequestDto;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.TodayNewsListResponseDto;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.TodayNewsReadLogResponseDto;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.TrendingNewsListResponseDto;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.TrendingNewsResponseDto;
import org.newshabit.app.news.infrastructure.adapter.inbound.web.dto.mapper.NewsDtoMapper;
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
	private final NewsDtoMapper dtoMapper;

	@GetMapping("/v2/member/today-news")
	public ResponseEntity<CommonResponse<TodayNewsListResponseDto>> getTodayNews(
		@AuthenticationPrincipal CustomUserDetail userDetail
	) {
		int userId = userDetail.getUserId();

		List<NewsSimpleInfo> todayNews = refinedNewsUseCase.getTodayNews(userId);

		return ResponseEntity.ok(CommonResponse.success(dtoMapper.toTodayNewsListResponseDto(todayNews)));
	}

	@PostMapping("/v2/guest/read-articles")
	public ResponseEntity<CommonResponse<Void>> updateNewsReadHistory(
		@AuthenticationPrincipal CustomUserDetail userDetail,
		@RequestBody NewsReadLogRequestDto requestDto
	) {
		Integer userId = userDetail != null ? userDetail.getUserId() : null;

		newsReadLogUseCase.updateNewsReadLog(userId, requestDto.newsId());

		return ResponseEntity.ok(CommonResponse.success());
	}

	@GetMapping("/v2/member/bookmarks")
	public ResponseEntity<CommonResponse<BookmarkListResponseDto>> getBookmarks(
			@AuthenticationPrincipal CustomUserDetail userDetail
	) {
		Integer userId = userDetail.getUserId();

		List<NewsSimpleInfo> bookmarkedNewsList = bookmarkUseCase.getBookmarkedNews(userId);

		return ResponseEntity.ok(CommonResponse.success(dtoMapper.toBookmarkListResponseDto(bookmarkedNewsList)));
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

	@DeleteMapping("/v2/member/bookmarks")
	public ResponseEntity<CommonResponse<Void>> deleteBookmarks(
		@AuthenticationPrincipal CustomUserDetail userDetail,
		@RequestBody BookmarkRequestDto requestDto
	) {
		Integer userId = userDetail.getUserId();

		bookmarkUseCase.deleteBookmark(userId, requestDto.newsId());

		return ResponseEntity.ok(CommonResponse.success());
	}

	@GetMapping("/v2/guest/trending")
	public ResponseEntity<CommonResponse<TrendingNewsListResponseDto>> getTrendingNews(
			@RequestParam(name = "page", required = false, defaultValue = "0") int page
	) {
		List<NewsSimpleInfo> trendingNews = refinedNewsUseCase.getTrendingNews(page);

		return ResponseEntity.ok(CommonResponse.success(dtoMapper.toTrendingNewsListResponseDto(trendingNews)));
	}

	@GetMapping("/v2/member/records")
	public ResponseEntity<CommonResponse<TodayNewsReadLogResponseDto>> getNewsReadRecords(
		@AuthenticationPrincipal CustomUserDetail userDetail,
		@RequestParam(name = "year") int year,
		@RequestParam(name = "month") int month
	) {
		Integer userId = userDetail.getUserId();

		List<TodayNewsReadLog> readLogs = newsReadLogUseCase.getNewsReadRecords(userId, year, month);

		TodayNewsReadLogResponseDto responseDto = new TodayNewsReadLogResponseDto(readLogs);

		return ResponseEntity.ok(CommonResponse.success(responseDto));
	}
}
