package com.gto.newsHabit.data;

import java.time.LocalDateTime;

import com.gto.newsHabit.data.type.NewsCategory;

import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class News {
	@Id
	@Column(name = "naver_url", length = 128)
	private String naverUrl;

	@NotNull
	@Column(name = "title", length = 128)
	private String title;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "category", length = 30)
	private NewsCategory category;

	@NotNull
	@Column(name = "date_time")
	private LocalDateTime dateTime;

	@Nullable
	@Column(name = "img_link", length = 128)
	private String imgLink;

	@NotNull
	@Column(name = "description", length = 256)
	private String description;

	@Builder
	public News(String naverUrl, String title, NewsCategory category, LocalDateTime dateTime, String imgLink, String description) {
		this.naverUrl = naverUrl;
		this.title = title;
		this.category = category;
		this.dateTime = dateTime;
		this.imgLink = imgLink;
		this.description = description;
	}
}
