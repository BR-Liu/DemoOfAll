package com.brliu.configure;


import com.brliu.aspect.RedisAccessLimiterAspect;
import com.brliu.properties.LuaScriptProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;


public class EnableAccessLimiterConfiguration {

    @Bean(name = "accessLimiterRedisTemplate")
    public StringRedisTemplate accessLimiterRedisTemplate(
            RedisConnectionFactory factory) {
        return new StringRedisTemplate(factory);
    }

    @Bean(name = "connLimiterRedisScript")
    public DefaultRedisScript connLimiterRedisScript(LuaScriptProperties luaScriptProperties) {
        DefaultRedisScript redisScript = new DefaultRedisScript();
        redisScript.setLocation(new ClassPathResource(luaScriptProperties.getClassPath() + luaScriptProperties.getFileName()));
        redisScript.setResultType(java.lang.Boolean.class);
        return redisScript;
    }

    @Bean
    public RedisAccessLimiterAspect redisAccessLimiterAspect(StringRedisTemplate accessLimiterRedisTemplate, DefaultRedisScript connLimiterRedisScript) {
        return new RedisAccessLimiterAspect(accessLimiterRedisTemplate, connLimiterRedisScript);
    }

}
