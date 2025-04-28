package org.newshabit.app.user.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.newshabit.app.common.domain.enums.UserRole;
import org.newshabit.app.user.domain.converter.NewsCategoryListConverter;
import org.newshabit.app.common.domain.enums.NewsCategory;
import org.newshabit.app.user.domain.dto.RegisterRequest;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "username", nullable = false, length = 50)
	private String username;

	@Column(name = "username_modified_at", nullable = false)
	private LocalDateTime usernameModifiedAt;

	@Column(name = "interest_categories", nullable = false, length = 255)
	@Convert(converter = NewsCategoryListConverter.class)
	private List<NewsCategory> interestCategories;

	@Column(name = "social_id", nullable = false, unique = true, length = 100)
	private String socialId;

	@Column(name = "role", nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private UserRole role;

	@OneToMany(mappedBy = "user",
		cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH },
		orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<AuthEntity> authList = new ArrayList<>();

	@OneToMany(mappedBy = "user",
		cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH },
		orphanRemoval = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private List<UserDailyGoalLogEntity> dailyGoalList = new ArrayList<>();

	private UserEntity(String username, LocalDateTime usernameModifiedAt, List<NewsCategory> interestCategories, String socialId, UserRole role) {
		this.id = null;
		this.username = username;
		this.usernameModifiedAt = usernameModifiedAt;
		this.interestCategories = interestCategories;
		this.socialId = socialId;
		this.role = role;
	}

	public static UserEntity from(RegisterRequest registerRequest) {
		return new UserEntity(
			registerRequest.username(),
			LocalDateTime.now().minusDays(14),
			registerRequest.categoryList(),
			registerRequest.socialId(),
			UserRole.MEMBER
		);
	}

}
