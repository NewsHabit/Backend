package org.newshabit.app.crawl.application.port;

import java.util.List;
import org.newshabit.app.common.domain.entity.CrawledNews;
import org.newshabit.app.common.domain.enums.NewsCategory;

public interface CrawlOutputPort {
	List<String> crawlHeadlineUrls(String url, NewsCategory category);
	CrawledNews crawlNews(String url, NewsCategory category);
}
