package edu.pollub.galleryservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "gcs")
public class ConfigProperties {
    private String clientId;
    private String clientSecret;
    private String quotaProjectId;
    private String refreshToken;
    private String type;
}
