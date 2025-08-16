package org.newshabit.app.news.application.service;

import lombok.RequiredArgsConstructor;
import org.newshabit.app.news.application.port.input.BookmarkUseCase;
import org.newshabit.app.news.application.port.output.BookmarkPort;
import org.newshabit.app.news.application.port.output.RefinedNewsPort;
import org.newshabit.app.news.domain.model.Bookmark;
import org.newshabit.app.news.domain.model.NewsSimpleInfo;
import org.newshabit.app.user.common.exception.ErrorCode;
import org.newshabit.app.user.common.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookmarkService implements BookmarkUseCase {
    private final BookmarkPort bookmarkPort;
    private final RefinedNewsPort refinedNewsPort;

    @Override
    public List<NewsSimpleInfo> getBookmarkedNews(int userId) {
        List<Bookmark> bookMarks = bookmarkPort.findAllByUserId(userId);

        List<Integer> newsIds = bookMarks.stream()
                .map(Bookmark::getNewsId)
                .toList();

        return refinedNewsPort.findAllByNewsIds(newsIds).stream().map(
            refinedNews -> new NewsSimpleInfo(
                refinedNews.getId(),
                refinedNews.getTitle(),
                refinedNews.getNewsCategory(),
                refinedNews.getSummary()
            )
        ).toList();
    }

    @Override
    public void addBookmark(int userId, int newsId) {
        refinedNewsPort.findById(newsId);

        Bookmark bookmark = new Bookmark(
                null,
                userId,
                newsId,
                LocalDateTime.now()
        );

        bookmarkPort.save(bookmark);
    }

    @Override
    public void deleteBookmark(int userId, int newsId) {
        Bookmark bookmark = bookmarkPort.findByUserIdAndNewsId(userId, newsId).orElseThrow(
            () -> new NotFoundException(ErrorCode.BOOKMARK_NOT_FOUND)
        );
        bookmarkPort.delete(bookmark);
    }
}
