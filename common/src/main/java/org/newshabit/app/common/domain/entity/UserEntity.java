package org.newshabit.app.common.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.newshabit.app.common.domain.converter.NewsCategoryListConverter;
import org.newshabit.app.common.domain.enums.NewsCategory;
import org.newshabit.app.common.domain.enums.UserRole;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "user")
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50)
	private String userName;

	@Column(nullable = false)
	private LocalDateTime userNameLastModified;

	@Column(nullable = false, length = 128)
	@Convert(converter = NewsCategoryListConverter.class)
	private List<NewsCategory> interestedCategory;

	@Column(nullable = false, unique = true)
	private String authToken;

	@Column(nullable = false)
	private LocalDateTime authTokenLastModified;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole userRole;
}
