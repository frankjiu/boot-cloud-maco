package com.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解类
 */
@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAssist {

	/**
	 * 操作类型(CRUD)
	 */
	public String operationType() default "";

	/**
	 * 功能业务模块(功能模块)
	 */
	public String operationModule() default "";

	/**
	 * 操作描述
	 */
	public String describe() default "";

}
