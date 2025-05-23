package org.newshabit.app.user.application.port.input;

import org.newshabit.app.user.domain.model.MemberSettings;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface MemberUserCase {
	MemberSettings getMemberSettings(int userId) throws NotFoundException;
}
