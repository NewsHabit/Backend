package org.newshabit.app.news.domain.model;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TodayNews {
	private Integer id;
	private Integer newsId;
	private LocalDate publishedAt;
}
