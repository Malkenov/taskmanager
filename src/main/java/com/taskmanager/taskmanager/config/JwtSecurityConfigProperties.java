package com.taskmanager.taskmanager.config;

import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@ConfigurationProperties(prefix = "application.security.jwt")
public class JwtSecurityConfigProperties {

    private String secretKey;
    private AccessToken accessToken;
    private RefreshToken refreshToken;


    @Getter
    @Setter
    public static class AccessToken{
        private Long expiration;
    }

    @Getter
    @Setter
    public static class RefreshToken{
        private Long expiration;
    }
}
