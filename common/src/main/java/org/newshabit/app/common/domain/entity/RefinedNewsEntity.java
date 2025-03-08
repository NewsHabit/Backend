package org.newshabit.app.common.domain.entity;

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
import org.newshabit.app.common.domain.enums.NewsCategory;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "refined_news")
public class RefinedNewsEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT")
	private String whoSummary;

	@Column(columnDefinition = "TEXT")
	private String whenSummary;

	@Column(columnDefinition = "TEXT")
	private String whereSummary;

	@Column(columnDefinition = "TEXT")
	private String whatSummary;

	@Column(columnDefinition = "TEXT")
	private String whySummary;

	@Column(columnDefinition = "TEXT")
	private String howSummary;

	@Column(columnDefinition = "TEXT")
	private String keyword;

	@Column(columnDefinition = "TEXT")
	private String summary;

	@Column(nullable = false)
	private LocalDateTime publishedAt;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private NewsCategory newsCategory;

	@Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
	private Long clickCnt;

	@Column(nullable = false)
	private String originalUrl;
}
