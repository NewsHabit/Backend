package org.newshabit.app.news.application.port.input;

import org.newshabit.app.news.domain.model.RefinedNews;

import java.util.List;

public interface BookmarkUseCase {
    List<RefinedNews> getBookmarkedNews(int userId);
    void addBookmark(int userId, int newsId);
}
