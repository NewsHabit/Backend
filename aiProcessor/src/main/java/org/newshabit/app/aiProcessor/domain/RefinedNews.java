package org.newshabit.app.aiProcessor.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
public class RefinedNews {

	@Id
	private Long id;

	@Column
	private String url;
}
