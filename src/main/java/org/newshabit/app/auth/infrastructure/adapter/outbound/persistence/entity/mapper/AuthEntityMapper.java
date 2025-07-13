package org.newshabit.app.auth.infrastructure.adapter.outbound.persistence.entity.mapper;

import org.newshabit.app.auth.domain.model.Auth;
import org.newshabit.app.auth.infrastructure.adapter.outbound.persistence.entity.AuthEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthEntityMapper {
	public Auth toDomain(AuthEntity authEntity) {
		return new Auth(
			authEntity.getId(),
			authEntity.getUserId(),
			authEntity.getDeviceId(),
			authEntity.getRefreshToken(),
			authEntity.getPublishedAt()
		);
	}
}
