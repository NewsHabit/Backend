package org.newshabit.app.crawl.application.usecase;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.crawl.application.port.CrawlInputPort;
import org.newshabit.app.crawl.application.port.CrawlOutputPort;
import org.newshabit.app.crawl.domain.Category;
import org.newshabit.app.crawl.domain.News;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrawlUseCase implements CrawlInputPort {
	private final CrawlOutputPort crawlOutputPort;

	@Override
	public List<String> crawlNewsUrl(Category category, int limitRank) {
		return null;
	}

	@Override
	public List<News> crawlNewsContents(List<String> urls) {
		return null;
	}
}
