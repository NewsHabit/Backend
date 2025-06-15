package org.newshabit.app.news.application.schedule;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import org.newshabit.app.news.application.port.output.RefinedNewsRepositoryOutputPort;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.entity.RefinedNewsEntity;
import org.newshabit.app.news.infrastructure.adapter.outbound.persistence.repository.RefinedNewsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsDeleteScheduler {
    private final RefinedNewsRepository newsRepository;
    private final RefinedNewsRepositoryOutputPort newsRepositoryOutputPort;

    @Value("${app.news.delete.click_cnt}")
    private int clickCntThreshold;

    @Value("${app.news.delete.date}")
    private int deleteBeforeDays;

    @Scheduled(cron = "${app.news.delete.cron}")
    public void deleteOldNews() {
        log.info("NewsDeleteScheduler started: {}", LocalDateTime.now());
        try {
            LocalDateTime border = LocalDateTime.now().minusDays(deleteBeforeDays);
            List<RefinedNewsEntity> targets = newsRepositoryOutputPort.findDeletableNews(clickCntThreshold, border);
            targets.forEach(n -> {
                log.info("Deleting news id: {}", n.getId());
                newsRepository.delete(n);
            });
        } catch (Exception e) {
            log.error("NewsDeleteScheduler error: {}", e.getMessage());
        }
        log.info("NewsDeleteScheduler finished: {}", LocalDateTime.now());
    }
}
