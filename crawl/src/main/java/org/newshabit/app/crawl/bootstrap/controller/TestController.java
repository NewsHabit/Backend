package org.newshabit.app.crawl.bootstrap.controller;

import org.newshabit.app.crawl.application.port.TestInputPort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/common")
public class TestController {
	private final TestInputPort testInputPort;

	@GetMapping("/test")
	public String test() {
		testInputPort.doSomething();
		return "Good";
	}
}
