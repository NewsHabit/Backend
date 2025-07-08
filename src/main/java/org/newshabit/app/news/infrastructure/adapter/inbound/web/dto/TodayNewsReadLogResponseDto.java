package org.newshabit.app.news.infrastructure.adapter.inbound.web.dto;

import org.newshabit.app.news.domain.model.TodayNewsReadLog;

import java.util.List;

public record TodayNewsReadLogResponseDto(
    List<TodayNewsReadLog> todayNewsReadRecords
) {}
