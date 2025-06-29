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

	/**
	* 사용자 엔티티를 삭제합니다.
	* 연관된 데이터는 DB 제약조건에 따라 함께 삭제됩니다.
	* @param userId 삭제할 사용자 ID
	*/
	void deleteById(int userId);
}
