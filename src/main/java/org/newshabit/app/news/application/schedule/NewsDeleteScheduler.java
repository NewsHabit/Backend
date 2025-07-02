package org.newshabit.app.news.application.schedule;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.news.application.port.output.RefinedNewsPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsDeleteScheduler {
    private final RefinedNewsPort newsRepositoryOutputPort;

    @Value("${app.news.delete.click_cnt}")
    private int clickCntThreshold;

    @Value("${app.news.delete.date}")
    private int deleteBeforeDays;

    @Scheduled(cron = "${app.news.delete.cron}")
    public void deleteOldNews() {
        log.info("NewsDeleteScheduler started: {}", LocalDateTime.now());
        try {
            LocalDateTime border = LocalDateTime.now().minusDays(deleteBeforeDays);
            newsRepositoryOutputPort.findDeletableNews(clickCntThreshold, border);
        } catch (Exception e) {
            log.error("NewsDeleteScheduler error: {}", e.getMessage());
        }
        log.info("NewsDeleteScheduler finished: {}", LocalDateTime.now());
    }
}
