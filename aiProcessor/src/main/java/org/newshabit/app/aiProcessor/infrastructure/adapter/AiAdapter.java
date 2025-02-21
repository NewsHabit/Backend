package org.newshabit.app.aiProcessor.infrastructure.adapter;

import org.newshabit.app.aiProcessor.application.port.AiOutputPort;
import org.newshabit.app.aiProcessor.domain.RefinedNews;
import org.newshabit.app.common.domain.entity.CrawledNews;
import org.springframework.stereotype.Component;

@Component
public class AiAdapter implements AiOutputPort {

	@Override
	public RefinedNews aiProcessNews(CrawledNews crawledNews) {
		return null;
	}
}
