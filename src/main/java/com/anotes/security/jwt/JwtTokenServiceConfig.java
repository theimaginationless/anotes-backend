package com.anotes.security.jwt;

import com.anotes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtTokenServiceConfig {

    private final JwtProperties jwtProps;
    private final UserService userService;

    @Bean
    public JwtTokenService jwtTokenService() {
        return new JwtTokenService(jwtProps, userService);
    }
}
