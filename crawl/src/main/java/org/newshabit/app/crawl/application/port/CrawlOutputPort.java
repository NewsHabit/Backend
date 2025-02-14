package org.newshabit.app.crawl.application.port;

import java.util.List;
import org.newshabit.app.crawl.domain.News;

public interface CrawlOutputPort {
	public List<String> crawlHeadlineUrls(String url);
	public News crawlNews(String url);
}
