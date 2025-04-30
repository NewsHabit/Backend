package org.newshabit.app.user.infrastructure.client;

import org.newshabit.app.common.response.CommonResponse;
import org.newshabit.app.user.domain.dto.LoginTokenPublishRequest;
import org.newshabit.app.user.domain.dto.LoginTokenPublishResponse;
import org.newshabit.app.user.infrastructure.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
	name = "news-service",
	url  = "${feign.news.url}",
	path = "/auth",
	configuration = FeignConfig.class
)public interface AuthClient {
	@PostMapping("/v2/internal/login")
	CommonResponse<LoginTokenPublishResponse> getTokens(@RequestBody LoginTokenPublishRequest request);
}
