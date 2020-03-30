package com.brliu.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "access.limit")
public class LuaScriptProperties {

    private String classPath = "/script/";
    private String fileName = "rateLimiter.lua";

    public LuaScriptProperties() {
    }

    public LuaScriptProperties(String classPath, String fileName) {
        this.classPath = classPath;
        this.fileName = fileName;
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
