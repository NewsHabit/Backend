package org.newshabit.app.common.util;

import java.util.Random;

public class SleepUtil {
	private static final Random random = new Random();
	private static final int DEFAULT_START_TIME = 1000;
	private static final int DEFAULT_END_TIME = 2000;
	public static void randomSleep(int startTime, int endTime) {
		try {
			int sleepTime = startTime + random.nextInt(endTime);
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException("Sleep interrupted", e);
		}
	}

	public static void randomSleep() {
		randomSleep(DEFAULT_START_TIME, DEFAULT_END_TIME);
	}
}
