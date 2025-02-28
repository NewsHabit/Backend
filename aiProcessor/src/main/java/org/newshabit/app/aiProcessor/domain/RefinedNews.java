package org.newshabit.app.aiProcessor.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class RefinedNews {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT")
	private String who;

	@Column(columnDefinition = "TEXT")
	private String when;

	@Column(columnDefinition = "TEXT")
	private String where;

	@Column(columnDefinition = "TEXT")
	private String what;

	@Column(columnDefinition = "TEXT")
	private String why;

	@Column(columnDefinition = "TEXT")
	private String how;

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
