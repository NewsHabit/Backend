package org.newshabit.app.news.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TodayNewsReadLog {
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private boolean isSatisfied;
}
