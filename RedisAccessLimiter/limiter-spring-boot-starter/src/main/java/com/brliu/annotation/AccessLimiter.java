package com.brliu.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimiter {

    int limit() default 10;

    String methodName() default "";
}
