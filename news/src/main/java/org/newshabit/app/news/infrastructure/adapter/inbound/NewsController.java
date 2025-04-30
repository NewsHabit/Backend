package org.newshabit.app.news.infrastructure.adapter.inbound;

import lombok.RequiredArgsConstructor;
import org.newshabit.app.common.response.CommonResponse;
import org.newshabit.app.news.application.port.RefinedNewsRepositoryOutputPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/news")
public class NewsController {
	private final RefinedNewsRepositoryOutputPort refinedNewsRepositoryOutputPort;

	@GetMapping("/v2/internal/exist")
	public ResponseEntity<CommonResponse<Boolean>> existNews(@RequestParam("url") String url) {

		return ResponseEntity.ok(CommonResponse.success(refinedNewsRepositoryOutputPort.existsByOriginalUrl(url)));
	}
}
