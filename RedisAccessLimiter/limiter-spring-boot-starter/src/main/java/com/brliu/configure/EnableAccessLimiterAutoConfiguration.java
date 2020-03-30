package com.brliu.configure;


import com.brliu.properties.LuaScriptProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "spring.redis", name = {"host", "port"})
@EnableConfigurationProperties(LuaScriptProperties.class)
public class EnableAccessLimiterAutoConfiguration {
}
