package org.newshabit.app.user.domain.dto;

import java.util.List;
import org.newshabit.app.common.domain.enums.UserRole;

public record LoginTokenPublishRequest(
	String socialId,
	List<UserRole> roles
) {}
