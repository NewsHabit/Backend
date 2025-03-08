package org.newshabit.app.crawl.infrastructure.adapter.outbound;

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
import org.newshabit.app.common.domain.model.CrawledNews;
import org.newshabit.app.common.domain.enums.NewsCategory;
import org.newshabit.app.common.util.SleepUtil;
import org.newshabit.app.crawl.application.port.CrawlOutputPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CrawlAdapter implements CrawlOutputPort {
	@Value("${app.crawl.user-agent}")
	private String userAgent;
	@Value("${app.crawl.start-time:1000}")
	private String startTime;
	@Value("${app.crawl.end-time:3000}")
	private String endTime;

	@Override
	public List<String> crawlHeadlineUris(String uri, NewsCategory category) {
		try {
			Document document = fetchHtmlDocument(uri + category.getCode());

			return extractHeadlineUris(document);
		} catch (IOException e) {
			throw new RuntimeException("크롤링 중 오류 발생: " + e.getMessage(), e);
		}
	}

	private Document fetchHtmlDocument(String uri) throws IOException {
		return Jsoup.connect(uri)
			.userAgent(userAgent)
			.timeout(5000)
			.get();
	}

	private List<String> extractHeadlineUris(Document document) {
		Elements items = document.select("li.sa_item._SECTION_HEADLINE, li.sa_item._SECTION_HEADLINE.is_blind");

		return items.stream()
			.flatMap(item -> item.select("div.sa_text a[data-imp-index]").stream())
			.map(this::extractValidUri)
			.flatMap(Optional::stream)
			.collect(Collectors.toList());
	}

	private Optional<String> extractValidUri(Element link) {
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
	public CrawledNews crawlNews(String uri, NewsCategory category) {
		SleepUtil.randomSleep(Integer.parseInt(startTime) , Integer.parseInt(endTime));
		try {
			Document document = fetchHtmlDocument(uri);

			String title = extractTitle(document);
			String content = extractContent(document);

			return CrawledNews.create(title, content, uri, LocalDateTime.now(), category);
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
