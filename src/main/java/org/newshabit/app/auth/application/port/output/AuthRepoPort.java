package org.newshabit.app.auth.application.port.output;

import java.util.List;
import java.util.Optional;
import org.newshabit.app.auth.domain.model.Auth;
import org.newshabit.app.auth.infrastructure.adapter.outbound.persistence.entity.AuthEntity;

public interface AuthRepoPort {
    void save(AuthEntity entity);
    Optional<AuthEntity> findByUserIdAndDeviceId(int userId, String deviceId);
    void deleteExpiredTokens(List<Integer> ids);
    List<Auth> findAll();
    Optional<Auth> findByRefreshToken(String refreshToken);
}
