package org.newshabit.app.user.application.port.output;

import java.util.Optional;
import org.newshabit.app.user.domain.model.User;

public interface UserRepositoryOutputPort {
	User save(User user);
	Optional<User> findBySocialId(String socialId);
	Optional<User> findByUserId(int id);
}
