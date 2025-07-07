package org.newshabit.app.news.infrastructure.adapter.outbound.persistence;

import lombok.RequiredArgsConstructor;
import org.newshabit.app.news.application.port.output.BookmarkPort;
import org.newshabit.app.news.domain.model.Bookmark;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.mapper.NewsEntityMapper;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository.BookmarkRepo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BookmarkAdapter implements BookmarkPort {
    private final BookmarkRepo bookmarkRepo;
    private final NewsEntityMapper newsEntityMapper;

    @Override
    public List<Bookmark> findAllByUserId(int userId) {
        return bookmarkRepo.findAllByUserId(userId).stream()
                .map(newsEntityMapper::toDomain)
                .toList();
    }
}
