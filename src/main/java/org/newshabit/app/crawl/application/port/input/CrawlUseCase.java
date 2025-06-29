package org.newshabit.app.crawl.application.port.input;

import java.util.List;
import org.newshabit.app.avro.CrawledNews;

public interface CrawlUseCase {
	List<CrawledNews> crawlNews();
}
