package org.newshabit.app.auth.application.port.output;

import java.util.Optional;
import org.newshabit.app.auth.infrastructure.adapter.outbound.persistence.entity.AuthEntity;

public interface AuthRepositoryOutputPort {
        void save(AuthEntity entity);
    Optional<AuthEntity> findByUserIdAndDeviceId(int userId, String deviceId);

    /**
     * 주어진 사용자 ID에 해당하는 인증 데이터를 모두 삭제합니다.
     * @param userId 삭제할 사용자 ID
     */
    void deleteByUserId(int userId);
}
