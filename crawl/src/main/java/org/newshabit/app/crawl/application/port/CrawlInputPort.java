package org.newshabit.app.crawl.application.port;

import java.util.List;
import org.newshabit.app.crawl.domain.News;

public interface CrawlInputPort {
	List<News> crawlNews();
}
