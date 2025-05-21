package org.newshabit.app.user.domain.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.newshabit.app.common.domain.enums.NewsCategory;
import org.newshabit.app.common.domain.enums.UserRole;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
	private Integer id;
	private String username;
	private LocalDateTime usernameModifiedAt;
	private List<NewsCategory> interestCategories;
	private String socialId;
	private UserRole role;
}
