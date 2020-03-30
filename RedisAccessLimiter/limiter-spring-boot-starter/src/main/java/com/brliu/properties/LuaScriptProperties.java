package com.brliu.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "access.limit")
public class LuaScriptProperties {

    private String classPath = "/script/";
    private String fileName = "rateLimiter.lua";

}
