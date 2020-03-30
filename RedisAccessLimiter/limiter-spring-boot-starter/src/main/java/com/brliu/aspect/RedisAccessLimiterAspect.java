package com.brliu.aspect;

import com.brliu.annotation.AccessLimiter;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@Aspect
public class RedisAccessLimiterAspect {

    private StringRedisTemplate accessLimiterRedisTemplate;

    private DefaultRedisScript connLimiterRedisScript;

    public RedisAccessLimiterAspect(StringRedisTemplate accessLimiterRedisTemplate, DefaultRedisScript connLimiterRedisScript) {
        this.accessLimiterRedisTemplate = accessLimiterRedisTemplate;
        this.connLimiterRedisScript = connLimiterRedisScript;
    }

    @Pointcut("@annotation(com.brliu.annotation.AccessLimiter)")
    public void limit() {
    }

    @Before(value = "limit()")
    public void beforeInvoke(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        AccessLimiter annotation = method.getAnnotation(AccessLimiter.class);
        if (annotation == null) {
            return;
        }

        String key = annotation.methodName();
        int limit = annotation.limit();

        // methodName, 从调用方法签名生成自动一个key
        if (StringUtils.isEmpty(key)) {
            Class[] type = method.getParameterTypes();
            key = method.getClass() + method.getName();

            String paramTypes = Arrays.stream(type)
                    .map(Class::getName)
                    .collect(Collectors.joining(","));
            key += "#" + paramTypes;
        }

        // 2. 调用Redis
        boolean acquired = (boolean) accessLimiterRedisTemplate.execute(
                connLimiterRedisScript,
                Collections.singletonList(key),
                Integer.toString(limit)
        );

        if (!acquired) {
            throw new RuntimeException("Your access is blocked");
        }
    }
}
