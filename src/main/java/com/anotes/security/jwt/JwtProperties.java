package com.anotes.security.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("jwt")
@Data
@NoArgsConstructor
public class JwtProperties {

    private String secretKey;
    private String header;
    private String tokenPrefix;
    private Integer tokenExpirationAfterDays;
}
