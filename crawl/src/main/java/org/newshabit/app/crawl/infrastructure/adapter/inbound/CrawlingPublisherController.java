package org.newshabit.app.crawl.infrastructure.adapter.inbound;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.avro.CrawledNews;
import org.newshabit.app.common.response.CommonResponse;
import org.newshabit.app.crawl.application.port.CrawlUseCase;
import org.newshabit.app.crawl.application.port.MessageUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/admin/crawl")
public class CrawlingPublisherController {
	private final CrawlUseCase crawlUseCase;
	private final MessageUseCase messageUseCase;

	@GetMapping("/produce")
	public ResponseEntity<CommonResponse<String>> crawlNewsAndProduce() {
		try {
			List<CrawledNews> crawledNewsList = crawlUseCase.crawlNews();
			messageUseCase.publishCrawledNews(crawledNewsList);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CommonResponse.error(500, e.getMessage()));
		}

		return ResponseEntity.ok(CommonResponse.success("crawled successfully"));
	}
}
