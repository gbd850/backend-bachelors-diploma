package edu.pollub.galleryservice.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "mail")
public class ConfigProperties {
    private String clientId;
    private String clientSecret;
    private String quotaProjectId;
    private String refreshToken;
    private String type;
}
