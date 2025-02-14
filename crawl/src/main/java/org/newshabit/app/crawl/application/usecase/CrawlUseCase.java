package org.newshabit.app.crawl.application.usecase;

import lombok.RequiredArgsConstructor;
import org.newshabit.app.crawl.application.port.CrawlInputPort;
import org.newshabit.app.crawl.application.port.CrawlOutputPort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrawlUseCase implements CrawlInputPort {
	private final CrawlOutputPort crawlOutputPort;

	@Override
	public void crawlNews() {

	}
}
