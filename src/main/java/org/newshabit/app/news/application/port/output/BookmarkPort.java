package org.newshabit.app.news.application.port.output;

import org.newshabit.app.news.domain.model.Bookmark;

import java.util.List;

public interface BookmarkPort {
    List<Bookmark> findAllByUserId(int userId);
}
