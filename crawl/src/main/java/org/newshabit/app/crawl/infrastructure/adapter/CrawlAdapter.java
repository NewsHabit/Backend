package org.newshabit.app.crawl.infrastructure.adapter;

import java.io.IOException;
import java.util.ArrayList;
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
			String userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36";
			Document document = Jsoup.connect(url).userAgent(userAgent).get();

			List<String> headlineUrls = new ArrayList<>();

			Elements items = document.select("li.sa_item._SECTION_HEADLINE, li.sa_item._SECTION_HEADLINE.is_blind");
			for (Element item : items) {
				Elements links = item.select("div.sa_text a[data-imp-index]");
				for (Element link : links) {
					String indexStr = link.attr("data-imp-index");
					try {
						int index = Integer.parseInt(indexStr);
						if (1 <= index && index <= 10) {
							String href = link.attr("href");
							headlineUrls.add(href);
						}
					} catch (NumberFormatException e) {
						System.out.println("잘못된 data-imp-index 값: " + indexStr);
					}
				}
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

	public static void main(String[] args) {
		try {
			CrawlAdapter crawlAdapter = new CrawlAdapter();
			String url = "https://news.naver.com/section/100";
			crawlAdapter.crawlHeadlineUrls(url);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
