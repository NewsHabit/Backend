package org.newshabit.app.crawl.bootstrap.controller;

import lombok.RequiredArgsConstructor;
import org.newshabit.app.crawl.application.port.CrawlInputPort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/crawl")
public class CrawlController {
	private final CrawlInputPort crawlInputPort;
	@GetMapping
	@Scheduled(cron = "0 0 * * * *")
	public void crawl() {

	}
}
