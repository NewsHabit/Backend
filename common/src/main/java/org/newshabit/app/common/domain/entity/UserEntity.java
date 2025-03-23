package org.newshabit.app.common.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
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

	@Column(name = "username_last_modified", nullable = false)
	private LocalDateTime usernameLastModified;

	@Column(name = "interested_category", nullable = false, length = 128)
	@Convert(converter = NewsCategoryListConverter.class)
	private List<NewsCategory> interestedCategory;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private UserAuthEntity userAuth;
}
