package com.semihunaldi.intellij.plugin.backend.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 *
 */

@Data
@ConfigurationProperties(prefix = "app.properties")
public class AppProperties
{
    private String testProperty;
    private String environment;
}
