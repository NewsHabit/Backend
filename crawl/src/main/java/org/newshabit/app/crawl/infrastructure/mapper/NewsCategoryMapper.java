package org.newshabit.app.crawl.infrastructure.mapper;

import java.util.List;
import org.newshabit.app.avro.NewsCategory;

public class NewsCategoryMapper {
	public static org.newshabit.app.avro.NewsCategory toAvro(org.newshabit.app.common.domain.enums.NewsCategory category) {
		return org.newshabit.app.avro.NewsCategory.valueOf(category.name());
	}

	public static org.newshabit.app.common.domain.enums.NewsCategory toDomain(org.newshabit.app.avro.NewsCategory avroCategory) {
		return org.newshabit.app.common.domain.enums.NewsCategory.valueOf(avroCategory.name());
	}

	public static List<NewsCategory> toAvroList(List<org.newshabit.app.common.domain.enums.NewsCategory> list) {
		return list.stream().map(NewsCategoryMapper::toAvro).toList();
	}

	public static List<org.newshabit.app.common.domain.enums.NewsCategory> toDomainList(List<org.newshabit.app.avro.NewsCategory> list) {
		return list.stream().map(NewsCategoryMapper::toDomain).toList();
	}
}

