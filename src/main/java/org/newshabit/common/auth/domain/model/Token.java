package org.newshabit.common.auth.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Token {
	public String accessToken;
	public String refreshToken;
}
