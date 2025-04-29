package org.newshabit.app.news.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.newshabit.app.avro.NewsCategory;
import org.newshabit.app.avro.RefinedNews;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "news")
public class RefinedNewsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "title", nullable = false, length = 255)
	private String title;

	@Column(name = "who_summary", length = 255)
	private String whoSummary;

	@Column(name = "when_summary", length = 255)
	private String whenSummary;

	@Column(name = "where_summary", length = 255)
	private String whereSummary;

	@Column(name = "what_summary", length = 255)
	private String whatSummary;

	@Column(name = "why_summary", length = 255)
	private String whySummary;

	@Column(name = "how_summary", length = 255)
	private String howSummary;

	@Column(name = "keyword", length = 255)
	private String keyword;

	@Column(name = "summary", length = 255)
	private String summary;

	@Column(name = "published_at", nullable = false)
	private LocalDateTime publishedAt;

	@Enumerated(EnumType.STRING)
	@Column(name = "news_category", nullable = false)
	private NewsCategory newsCategory;

	@Column(name = "click_cnt", nullable = false)
	private Integer clickCnt;

	@Column(name = "original_url", nullable = false)
	private String originalUrl;

	public static RefinedNewsEntity fromAvro(RefinedNews refinedNews) {
		return new RefinedNewsEntity(
			null,
			refinedNews.getTitle(),
			refinedNews.getWhoSummary(),
			refinedNews.getWhenSummary(),
			refinedNews.getWhereSummary(),
			refinedNews.getWhatSummary(),
			refinedNews.getWhySummary(),
			refinedNews.getHowSummary(),
			refinedNews.getKeyword(),
			refinedNews.getSummary(),
			LocalDateTime.now(),
			refinedNews.getNewsCategory(),
			refinedNews.getClickCnt(),
			refinedNews.getOriginalUrl()
		);
	}
}
