package org.newshabit.app.news.infrastructure.adapter.outbound.persistence;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.news.application.port.output.BookmarkPort;
import org.newshabit.app.news.domain.model.Bookmark;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.BookmarkEntity;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.mapper.NewsEntityMapper;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository.BookmarkRepo;
import org.newshabit.app.user.common.exception.DuplicatedException;
import org.newshabit.app.user.common.exception.ErrorCode;
import org.newshabit.app.user.common.exception.NotFoundException;
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

    @Override
    public Bookmark save(Bookmark bookmark) {
        BookmarkEntity bookmarkEntity = newsEntityMapper.toEntity(bookmark);

        bookmarkRepo.findByUserIdAndNewsId(bookmarkEntity.getUserId(), bookmarkEntity.getNewsId()).ifPresent(
            a -> { throw new DuplicatedException(ErrorCode.BOOKMARK_DUPLICATED); }
        );

        return newsEntityMapper.toDomain(bookmarkRepo.save(bookmarkEntity));
    }

    @Override
    public Optional<Bookmark> findByUserIdAndNewsId(int userId, int newsId) {
        BookmarkEntity bookmarkEntity = bookmarkRepo.findByUserIdAndNewsId(userId, newsId).orElseThrow(
            () -> new NotFoundException(ErrorCode.BOOKMARK_NOT_FOUND)
        );

        return Optional.of(newsEntityMapper.toDomain(bookmarkEntity));
    }

    @Override
    public void delete(Bookmark bookmark) {
        bookmarkRepo.delete(newsEntityMapper.toEntity(bookmark));
    }
}
