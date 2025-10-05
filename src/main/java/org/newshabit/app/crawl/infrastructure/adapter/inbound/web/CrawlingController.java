package org.newshabit.app.crawl.infrastructure.adapter.inbound.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.avro.CrawledNews;
import org.newshabit.app.common.response.CommonResponse;
import org.newshabit.app.crawl.application.port.input.CrawlUseCase;
import org.newshabit.app.crawl.application.port.input.MessageUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/v2/crawl")
public class CrawlingController {

	private final CrawlUseCase crawlUseCase;
	private final MessageUseCase messageUseCase;

	@GetMapping("/admin/produce")
	public ResponseEntity<CommonResponse<String>> crawlNewsAndProduce() {

		List<CrawledNews> crawledNewsList = crawlUseCase.crawlNews();
		messageUseCase.publishCrawledNews(crawledNewsList);

		return ResponseEntity.ok(CommonResponse.success("crawled successfully"));
	}

	@GetMapping("/admin/status")
	public ResponseEntity<CommonResponse<Boolean>> getCrawlStatus() {
		return ResponseEntity.ok(CommonResponse.success(
			crawlUseCase.getCrawlStatus()
		));
	}

	@PostMapping("/admin/status")
	public ResponseEntity<CommonResponse<Boolean>> updateCrawlStatus(
		@RequestParam boolean crawlEnabled) {
		crawlUseCase.setCrawlStatus(crawlEnabled);
		log.info("Crawl status updated to: {}", crawlEnabled);
		return ResponseEntity.ok(CommonResponse.success(crawlEnabled));
	}
}
