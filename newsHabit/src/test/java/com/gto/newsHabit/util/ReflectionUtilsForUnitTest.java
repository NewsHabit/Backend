package com.gto.newsHabit.util;

import java.lang.reflect.Field;

import com.gto.newsHabit.global.exception.ErrorCode;
import com.gto.newsHabit.global.exception.custom.CustomRuntimeException;

/**
 * ReflectionUtilsForUnitTest.
 *
 * <p>
 *  유닛 테스트를 위한 리플렉션 유틸리티
 * </p>
 *
 */
public class ReflectionUtilsForUnitTest {

	/**
	 * 리플렉션을 사용해서 필드값을 설정한다.
	 */
	public static void setFieldWithReflection(Object object, String fieldName, Object value) {
		try {
			Field field = object.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(object, value);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new CustomRuntimeException(ErrorCode.BAD_REQUEST);
		}
	}
}
