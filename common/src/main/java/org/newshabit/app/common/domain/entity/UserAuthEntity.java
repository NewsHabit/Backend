package org.newshabit.app.common.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.newshabit.app.common.domain.enums.UserRole;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_auth")
public class UserAuthEntity {
	@Id
	private Long userId;

	@OneToOne
	@MapsId
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@Column(name = "access_token", nullable = false, length = 255)
	private String accessToken;

	@Column(name = "access_token_last_modified", nullable = false)
	private LocalDateTime accessTokenLastModified;

	@Column(name = "refresh_token", nullable = false, length = 255)
	private String refreshToken;

	@Column(name = "refresh_token_last_modified", nullable = false)
	private LocalDateTime refreshTokenLastModified;

	@Column(name = "social_id", nullable = false, length = 255)
	private String socialId;

	@Enumerated(EnumType.STRING)
	@Column(name = "user_role", nullable = false, length = 50)
	private UserRole userRole;
}
