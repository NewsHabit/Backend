package org.newshabit.app.crawl.application.port.output;

import java.util.List;
import org.newshabit.app.avro.CrawledNews;
import org.newshabit.app.avro.NewsCategory;

public interface CrawlPort {

	List<String> crawlHeadlineUris(String url, NewsCategory category) throws RuntimeException;

	CrawledNews crawlNews(String url, NewsCategory category) throws RuntimeException;

	boolean isCrawlEnabled();

	void setCrawlEnabled(boolean enabled);
}
