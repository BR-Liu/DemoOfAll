package com.brliu.annotation;


import com.brliu.configure.EnableAccessLimiterConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Configuration
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Import(EnableAccessLimiterConfiguration.class)
public @interface EnableAccessLimiter {
}
