package org.newshabit.app.crawl.infrastructure.adapter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.newshabit.app.common.domain.entity.CrawledNews;
import org.newshabit.app.common.domain.enums.NewsCategory;
import org.newshabit.app.common.util.SleepUtil;
import org.newshabit.app.crawl.application.port.CrawlOutputPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CrawlAdapter implements CrawlOutputPort {
	private static final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36";
	@Value("${app.crawl.start-time:1000}")
	private String startTime;
	@Value("${app.crawl.end-time:3000}")
	private String endTime;

	@Override
	public List<String> crawlHeadlineUrls(String url, NewsCategory category) {
		try {
			Document document = fetchHtmlDocument(url + category.getCode());

			return extractHeadlineUrls(document);
		} catch (IOException e) {
			throw new RuntimeException("크롤링 중 오류 발생: " + e.getMessage(), e);
		}
	}

	private Document fetchHtmlDocument(String url) throws IOException {
		return Jsoup.connect(url)
			.userAgent(USER_AGENT)
			.timeout(5000)
			.get();
	}

	private List<String> extractHeadlineUrls(Document document) {
		Elements items = document.select("li.sa_item._SECTION_HEADLINE, li.sa_item._SECTION_HEADLINE.is_blind");

		return items.stream()
			.flatMap(item -> item.select("div.sa_text a[data-imp-index]").stream())
			.map(this::extractValidUrl)
			.flatMap(Optional::stream)
			.collect(Collectors.toList());
	}

	private Optional<String> extractValidUrl(Element link) {
		String indexStr = link.attr("data-imp-index");

		try {
			int index = Integer.parseInt(indexStr);
			if (1 <= index && index <= 10) {
				return Optional.of(link.attr("href"));
			}
		} catch (NumberFormatException ignored) {

		}
		return Optional.empty();
	}

	@Override
	public CrawledNews crawlNews(String url, NewsCategory category) {
		SleepUtil.randomSleep(Integer.parseInt(startTime) , Integer.parseInt(endTime));
		try {
			Document document = fetchHtmlDocument(url);

			String title = extractTitle(document);
			String content = extractContent(document);

			return CrawledNews.create(title, content, url, LocalDateTime.now(), category);
		} catch (IOException e) {
			throw new RuntimeException("크롤링 중 오류 발생: " + e.getMessage(), e);
		}
	}

	private String extractTitle(Document document) throws RuntimeException {
		return Optional.ofNullable(document.getElementById("title_area"))
			.map(Element::text)
			.orElseThrow(() -> new RuntimeException("제목을 찾을 수 없습니다."));
	}

	private String extractContent(Document document) throws RuntimeException {
		return Optional.ofNullable(document.getElementById("dic_area"))
			.map(element -> {
				element.select(".img_desc").remove();
				return element.text();
			})
			.orElseThrow(() -> new RuntimeException("본문을 찾을 수 없습니다."));
	}
}
