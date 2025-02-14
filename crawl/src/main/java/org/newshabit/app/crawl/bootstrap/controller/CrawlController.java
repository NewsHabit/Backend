package org.newshabit.app.crawl.bootstrap.controller;

import lombok.RequiredArgsConstructor;
import org.newshabit.app.common.domain.CommonResponse;
import org.newshabit.app.crawl.application.port.CrawlInputPort;
import org.newshabit.app.crawl.application.port.MessageInputPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/crawl")
public class CrawlController {
	private final CrawlInputPort crawlInputPort;
	private final MessageInputPort messageInputPort;
	@GetMapping("/produce")
	public ResponseEntity<CommonResponse<String>> crawlNewsAndProduce() {
		crawlInputPort.crawlNews();
		messageInputPort.publishCrawledNews();
		return ResponseEntity.ok(CommonResponse.success("crawled successfully"));
	}
}
