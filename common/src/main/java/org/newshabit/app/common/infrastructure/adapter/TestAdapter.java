package org.newshabit.app.common.infrastructure.adapter;

import org.newshabit.app.common.application.port.TestOutputPort;
import org.springframework.stereotype.Component;


@Component
public class TestAdapter implements TestOutputPort {
    @Override
    public void doSomething() {
        System.out.println("Test Adapter");
    }
}
