package org.newshabit.app.auth.infrastructure.adapter.outbound.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "auth")
public class AuthEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "user_id", nullable = false)
	private int userId;

	@Column(name = "device_id", nullable = false, length = 100)
	private String deviceId;

	@Setter
	@Column(name = "refresh_token", nullable = false, length = 1024)
	private String refreshToken;

	@Column(name = "published_at", nullable = false)
	private LocalDateTime publishedAt;

	@PrePersist
	protected void onCreate() {
		this.publishedAt = LocalDateTime.now();
	}

	public void modifyPublishedAt() {
		this.publishedAt = LocalDateTime.now();
	}
}
