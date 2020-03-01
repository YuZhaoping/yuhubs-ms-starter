package com.yuhubs.ms.web.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExceptionStatusMapper {

	Class<? extends Throwable>[] value() default {};

}
