package com.brliu.annotation;


import com.brliu.selector.LimiterSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Import(LimiterSelector.class)
public @interface EnableAccessLimiter {
}
