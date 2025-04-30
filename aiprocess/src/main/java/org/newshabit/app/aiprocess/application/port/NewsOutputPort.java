package org.newshabit.app.aiprocess.application.port;

import org.springframework.web.bind.annotation.RequestParam;

public interface NewsOutputPort {
	boolean existNews(@RequestParam("url") String url);
}
