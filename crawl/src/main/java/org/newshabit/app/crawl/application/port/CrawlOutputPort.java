package org.newshabit.app.crawl.application.port;

import java.util.List;
import org.newshabit.app.avro.CrawledNews;
import org.newshabit.app.avro.NewsCategory;

public interface CrawlOutputPort {
	List<String> crawlHeadlineUris(String url, NewsCategory category) throws RuntimeException ;
	CrawledNews crawlNews(String url, NewsCategory category) throws RuntimeException;
}
