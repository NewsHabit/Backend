package org.newshabit.app.crawl.application.usecase;

import org.newshabit.app.crawl.application.port.TestInputPort;
import org.springframework.stereotype.Service;

import org.newshabit.app.common.application.port.TestOutputPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TestUseCase implements TestInputPort {
	private final TestOutputPort testOutputPort;

	@Override
	public void doSomething() {
		testOutputPort.doSomething();
	}
}
