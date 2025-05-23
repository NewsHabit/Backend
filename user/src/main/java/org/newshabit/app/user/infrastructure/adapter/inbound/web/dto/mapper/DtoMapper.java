package org.newshabit.app.user.infrastructure.adapter.inbound.web.dto.mapper;

import org.newshabit.app.user.domain.model.MemberSettings;
import org.newshabit.app.user.domain.model.Register;
import org.newshabit.app.user.infrastructure.adapter.inbound.web.dto.RegisterRequest;
import org.newshabit.app.user.infrastructure.adapter.inbound.web.dto.SettingsResponse;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {
	public SettingsResponse toDto(MemberSettings memberSettings) {
		return new SettingsResponse(
			memberSettings.name(),
			memberSettings.categoryList(),
			memberSettings.dailyGoal()
		);
	}

	public Register toDomain(RegisterRequest registerRequest) {
		return new Register(
			registerRequest.socialId(),
			registerRequest.username(),
			registerRequest.categoryList(),
			registerRequest.dailyGoal()
		);
	}
}
