package org.newshabit.app.common.domain.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.newshabit.app.common.domain.enums.NewsCategory;

@Converter
public class NewsCategoryListConverter implements AttributeConverter<List<NewsCategory>, String> {

	private static final String SEPARATOR = ",";

	@Override
	public String convertToDatabaseColumn(List<NewsCategory> attribute) {
		return attribute != null ? attribute.stream()
			.map(Enum::name)
			.collect(Collectors.joining(SEPARATOR)) : null;
	}

	@Override
	public List<NewsCategory> convertToEntityAttribute(String dbData) {
		return dbData != null && !dbData.isEmpty() ?
			Arrays.stream(dbData.split(SEPARATOR))
				.map(NewsCategory::valueOf)
				.collect(Collectors.toList()) : null;
	}
}
