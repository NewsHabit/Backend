package org.newshabit.app.aiprocess.infrastructure.client;

import org.newshabit.app.common.response.CommonResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
	name = "news-service",
	url  = "${services.news.url}",
	path = "/news"
)public interface NewsClient {
	@GetMapping("/v2/admin/exist")
	CommonResponse<Boolean> existNews(@RequestParam("url") String url);
}
