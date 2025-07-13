package org.newshabit.app.auth.infrastructure.adapter.outbound.persistence;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.newshabit.app.auth.application.port.output.AuthRepoPort;
import org.newshabit.app.auth.domain.model.Auth;
import org.newshabit.app.auth.infrastructure.adapter.outbound.persistence.entity.AuthEntity;
import org.newshabit.app.auth.infrastructure.adapter.outbound.persistence.entity.mapper.AuthEntityMapper;
import org.newshabit.app.auth.infrastructure.adapter.outbound.persistence.repository.AuthRepo;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthRepoAdapter implements AuthRepoPort {
    private final AuthRepo authRepo;
    private final AuthEntityMapper authEntityMapper;

    @Override
    public void save(AuthEntity entity) {
        authRepo.save(entity);
    }

    public Optional<AuthEntity> findByUserIdAndDeviceId(int userId, String deviceId) {
            return authRepo.findAuthEntityByUserIdAndDeviceId(userId, deviceId);
        }

    @Override
    public void deleteExpiredTokens(List<Integer> ids) {
        authRepo.deleteAllById(ids);
    }

    @Override
    public List<Auth> findAll() {
        return authRepo.findAll().stream()
            .map(authEntityMapper::toDomain)
            .toList();
    }

    @Override
    public Optional<Auth> findByRefreshToken(String refreshToken) {
        return authRepo.findByRefreshToken(refreshToken)
            .map(authEntityMapper::toDomain);
    }
}
