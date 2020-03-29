package com.brliu.configure;


import com.brliu.properties.LuaScriptProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;

@Configuration
@ConditionalOnProperty(prefix = "spring.redis", name = {"host", "port"})
@ComponentScan({"com.brliu.*"})
@EnableConfigurationProperties(LuaScriptProperties.class)
public class EnableAccessLimiterConfiguration {

    @Bean(name = "accessLimiterRedisTemplate")
    public RedisTemplate<String, String> accessLimiterRedisTemplate(
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

}
