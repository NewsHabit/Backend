package org.newshabit.app.auth.application.port.output;

import java.util.List;
import org.newshabit.app.common.domain.enums.UserRole;

public interface TokenProviderOutputPort {
	String createAccessToken(String socialId, int userId, String deviceId, List<UserRole> roles);
	String createRefreshToken(String socialId, int userId, String deviceId, List<UserRole> roles);
}
