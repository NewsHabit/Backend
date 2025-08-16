package org.newshabit.app.news.application.port.input;

import org.newshabit.app.news.domain.model.NewsSimpleInfo;
import java.util.List;

public interface BookmarkUseCase {
    List<NewsSimpleInfo> getBookmarkedNews(int userId);
    void addBookmark(int userId, int newsId);
    void deleteBookmark(int userId, int newsId);
}
