package org.newshabit.app.auth.infrastructure.adapter.outbound.persistence;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<AuthEntity, Integer> {

	Optional<AuthEntity> findAuthEntityByUserIdAndDeviceId(int userId, String deviceId);
}
