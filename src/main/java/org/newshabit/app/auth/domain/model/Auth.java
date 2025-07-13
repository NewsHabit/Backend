package org.newshabit.app.auth.domain.model;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Auth {
	private Integer id;
	private Integer userId;
	private String deviceId;
	private String refreshToken;
	private LocalDateTime publishedAt;
}
