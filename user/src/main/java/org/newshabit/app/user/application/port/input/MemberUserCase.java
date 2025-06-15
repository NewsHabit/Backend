package org.newshabit.app.user.application.port.input;

import java.util.List;
import org.newshabit.app.common.domain.enums.NewsCategory;
import org.newshabit.app.user.domain.model.MemberSettings;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface MemberUserCase {
	MemberSettings getMemberSettings(int userId) throws NotFoundException;
	void updateUsername(int userId, String username) throws NotFoundException;
	void updateInterestCategories(int userId, List<NewsCategory> interestCategories) throws NotFoundException;
	void updateDailyGoal(int userId, int dailyGoal) throws NotFoundException;

	/**
	 * 사용자 계정을 삭제합니다.
	 * @param userId 삭제할 사용자의 아이디
	 */
	void deleteMember(int userId);
}
