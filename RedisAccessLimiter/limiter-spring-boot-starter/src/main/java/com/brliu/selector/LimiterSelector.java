package com.brliu.selector;


import com.brliu.configure.EnableAccessLimiterConfiguration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class LimiterSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{EnableAccessLimiterConfiguration.class.getName()};
    }
}

