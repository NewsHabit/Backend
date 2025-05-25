package org.newshabit.app.user.application.port.output;

import java.util.List;
import java.util.Optional;
import org.newshabit.app.common.domain.enums.NewsCategory;
import org.newshabit.app.user.domain.model.User;

public interface UserRepositoryOutputPort {
	User save(User user);
	Optional<User> findBySocialId(String socialId);
	Optional<User> findByUserId(int id);
	void updateUsername(User user, String username);
	void updateInterestCategories(User user, List<NewsCategory> interestCategories);
}
