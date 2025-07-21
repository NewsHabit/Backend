package org.newshabit.app.auth.application.port.output;

import java.util.List;
import java.util.Optional;
import org.newshabit.app.auth.domain.model.Auth;

public interface AuthRepoPort {
    void save(Auth entity);
    Optional<Auth> findByUserIdAndDeviceId(int userId, String deviceId);
    void deleteExpiredTokens(List<Integer> ids);
    List<Auth> findAll();
    Optional<Auth> findByRefreshToken(String refreshToken);
}
