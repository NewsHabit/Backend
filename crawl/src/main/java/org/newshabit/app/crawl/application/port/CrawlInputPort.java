package org.newshabit.app.crawl.application.port;

import java.util.List;
import org.newshabit.app.common.domain.model.CrawledNews;

public interface CrawlInputPort {
	List<CrawledNews> crawlNews();
}
