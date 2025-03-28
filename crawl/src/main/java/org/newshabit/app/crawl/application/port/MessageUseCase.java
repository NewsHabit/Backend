package org.newshabit.app.crawl.application.port;

import java.util.List;
import org.newshabit.app.avro.CrawledNews;

public interface MessageUseCase {
	void publishCrawledNews(List<CrawledNews> newsList);
}
