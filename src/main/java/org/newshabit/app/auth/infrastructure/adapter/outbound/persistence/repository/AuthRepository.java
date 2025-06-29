package org.newshabit.app.auth.infrastructure.adapter.outbound.persistence.repository;

import java.util.Optional;
import org.newshabit.app.auth.infrastructure.adapter.outbound.persistence.entity.AuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<AuthEntity, Integer> {

        Optional<AuthEntity> findAuthEntityByUserIdAndDeviceId(int userId, String deviceId);

        void deleteByUserId(int userId);
}
