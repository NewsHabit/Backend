package org.newshabit.app.news.application.port.output;

import java.util.Optional;
import org.newshabit.app.news.domain.model.Bookmark;

import java.util.List;

public interface BookmarkPort {
    List<Bookmark> findAllByUserId(int userId);
    Bookmark save(Bookmark bookmark);
    Optional<Bookmark> findByUserIdAndNewsId(int userId, int newsId);
    void delete(Bookmark bookmark);
}
