package org.newshabit.app.crawl.application.port;

import java.util.List;
import org.newshabit.app.crawl.domain.Category;
import org.newshabit.app.crawl.domain.News;

public interface CrawlOutputPort {
	List<String> crawlNewsUrl(Category category, int limitRank);
	News crawlNewsContents(List<String> urls);
}
