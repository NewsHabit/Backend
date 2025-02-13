package org.newshabit.app.crawl.infrastructure.adapter;

import java.util.List;
import org.newshabit.app.crawl.application.port.CrawlOutputPort;
import org.newshabit.app.crawl.domain.Category;
import org.newshabit.app.crawl.domain.News;
import org.springframework.stereotype.Component;

@Component
public class CrawlAdapter implements CrawlOutputPort {
	@Override
	public List<String> crawlNewsUrl(Category category, int limitRank) {
		return null;
	}

	@Override
	public News crawlNewsContents(List<String> urls) {
		return null;
	}
}
