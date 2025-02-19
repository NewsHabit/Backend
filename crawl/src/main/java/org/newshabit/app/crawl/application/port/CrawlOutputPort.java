package org.newshabit.app.crawl.application.port;

import java.util.List;
import org.newshabit.app.crawl.domain.Category;
import org.newshabit.app.crawl.domain.News;

public interface CrawlOutputPort {
	List<String> crawlHeadlineUrls(String url, Category category);
	News crawlNews(String url, Category category);
}
