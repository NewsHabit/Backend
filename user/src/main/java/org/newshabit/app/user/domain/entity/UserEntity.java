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
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.newshabit.app.common.domain.converter.NewsCategoryListConverter;
import org.newshabit.app.common.domain.enums.NewsCategory;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50)
	private String username;

	@Column(name = "username_modified_at", nullable = false)
	private LocalDateTime usernameModifiedAt;

	@Column(name = "interest_categories", length = 255)
	@Convert(converter = NewsCategoryListConverter.class)
	private List<NewsCategory> interestCategories;

	@Column(name = "social_id", nullable = false, unique = true, length = 100)
	private String socialId;

	@Column(nullable = false, length = 20)
	@Enumerated(EnumType.STRING)
	private String role;

	// 연관 관계 (읽기 전용 매핑)
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<AuthEntity> authList;
}
