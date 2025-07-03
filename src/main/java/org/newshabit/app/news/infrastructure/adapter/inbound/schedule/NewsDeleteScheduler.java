package org.newshabit.app.news.infrastructure.adapter.inbound.schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.newshabit.app.news.application.port.input.RefinedNewsUseCase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NewsDeleteScheduler {
    private final RefinedNewsUseCase refinedNewsUseCase;

    @Value("${app.news.delete.click_cnt}")
    private int clickCntThreshold;

    @Value("${app.news.delete.date}")
    private int deleteBeforeDays;

    @Scheduled(cron = "${app.news.delete.cron}")
    public void deleteBelowThresholdNews() {
        log.info("NewsDeleteScheduler started: {}", LocalDateTime.now());
        try {
            LocalDate border = LocalDate.now().minusDays(deleteBeforeDays);

            refinedNewsUseCase.deleteThresholdNews(clickCntThreshold, border);
        } catch (Exception e) {
            log.error("NewsDeleteScheduler error: {}", e.getMessage());
            throw new RuntimeException("NewsDeleteScheduler error: " + e.getMessage());
        }
        log.info("NewsDeleteScheduler finished: {}", LocalDateTime.now());
    }
}
