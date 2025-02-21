package org.newshabit.app.crawl.application.port;

import java.util.List;
import org.newshabit.app.common.domain.entity.CrawledNews;

public interface MessageInputPort {
	void publishCrawledNews(List<CrawledNews> newsList);
}
