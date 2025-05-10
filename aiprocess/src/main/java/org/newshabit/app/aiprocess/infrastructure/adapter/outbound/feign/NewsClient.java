package org.newshabit.app.aiprocess.infrastructure.adapter.outbound.feign;

import org.newshabit.app.aiprocess.infrastructure.config.FeignConfig;
import org.newshabit.app.common.response.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
	name = "news-service",
	url  = "${feign.news.url}",
	path = "/news",
	configuration = FeignConfig.class
)public interface NewsClient {
	@GetMapping("/v2/internal/exist")
	CommonResponse<Boolean> existNews(@RequestParam("url") String url);
}
