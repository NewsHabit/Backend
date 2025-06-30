package org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity;

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
import org.newshabit.app.common.domain.enums.NewsCategory;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "news_read_log")
public class NewsReadLogEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "user_id", nullable = false)
	private Integer userId;

	@Column(name = "news_id", nullable = false)
	private Integer newsId;

	@Enumerated(EnumType.STRING)
	@Column(name = "category", nullable = false)
	private NewsCategory category;

	@Column(name = "is_today_news", nullable = false)
	private boolean isTodayNews;

	@Column(name = "published_at", nullable = false)
	private LocalDateTime publishedAt;
}
