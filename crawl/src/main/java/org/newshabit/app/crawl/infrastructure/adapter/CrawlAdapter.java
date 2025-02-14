package org.newshabit.app.crawl.infrastructure.adapter;

import java.io.IOException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.newshabit.app.crawl.application.port.CrawlOutputPort;
import org.newshabit.app.crawl.domain.News;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CrawlAdapter implements CrawlOutputPort {

	@Override
	public List<String> crawlHeadlineUrls(String url) {
		try {
			Document document = Jsoup.connect(url).get();

			// 제목 가져오기
			String title = document.title();
			System.out.println("페이지 제목: " + title);

			// 모든 링크 가져오기
			Elements links = document.select("a[href]");
			System.out.println("\n페이지 내 링크 목록:");
			for (Element link : links) {
				System.out.println(link.text() + " -> " + link.attr("href"));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	@Override
	public News crawlNews(String url) {
		return null;
	}
}
