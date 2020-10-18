package com.anotes.security;

import com.anotes.security.jwt.JwtCredentialsAuthFilter;
import com.anotes.security.jwt.JwtProperties;
import com.anotes.security.jwt.JwtTokenService;
import com.anotes.security.jwt.JwtTokenVerifierFilter;
import com.anotes.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    private final ObjectMapper objectMapper;
    private final JwtProperties jwtProps;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterAt(
                        new RegistrationFilter(
                                userService,
                                passwordEncoder,
                                jwtTokenService,
                                jwtProps,
                                objectMapper
                        ),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterAfter(
                        new JwtCredentialsAuthFilter(
                                objectMapper,
                                authenticationManager(),
                                jwtTokenService,
                                jwtProps
                        ),
                        RegistrationFilter.class
                )
                .addFilterAfter(
                        new JwtTokenVerifierFilter(jwtTokenService),
                        JwtCredentialsAuthFilter.class
                )
                .authorizeRequests()
                .anyRequest().authenticated();
    }
}
