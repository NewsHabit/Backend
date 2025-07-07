package org.newshabit.app.news.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Bookmark {
    private Integer id;
    private Integer userId;
    private Integer newsId;
    private LocalDateTime publishedAt;
}
