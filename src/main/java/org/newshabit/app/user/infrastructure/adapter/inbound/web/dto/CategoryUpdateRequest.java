package org.newshabit.app.user.infrastructure.adapter.inbound.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import org.newshabit.app.common.domain.enums.NewsCategory;

public record CategoryUpdateRequest(
	@NotNull
	@Size(min = 1, max = 5, message = "최소 1개, 최대 5개까지 선택할 수 있습니다.")
	List<@NotNull NewsCategory> categories
) {}
