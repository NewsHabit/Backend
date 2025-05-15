package org.newshabit.app.crawl.infrastructure.adapter.inbound.web;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.avro.CrawledNews;
import org.newshabit.app.common.response.CommonResponse;
import org.newshabit.app.crawl.application.port.input.CrawlUseCase;
import org.newshabit.app.crawl.application.port.input.MessageUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crawl")
public class CrawlingPublisherController {
	private final CrawlUseCase crawlUseCase;
	private final MessageUseCase messageUseCase;

	@GetMapping("/v2/admin/produce")
	public ResponseEntity<CommonResponse<String>> crawlNewsAndProduce() {

		List<CrawledNews> crawledNewsList = crawlUseCase.crawlNews();
		messageUseCase.publishCrawledNews(crawledNewsList);

		return ResponseEntity.ok(CommonResponse.success("crawled successfully"));
	}
}
