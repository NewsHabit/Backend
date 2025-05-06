package org.newshabit.app.auth.domain.dto;

import java.util.List;
import org.newshabit.app.common.domain.enums.UserRole;

public record LoginTokenPublishRequest(
	String socialId,
	String deviceId,
	int userId,
	List<UserRole> roles
) {}
