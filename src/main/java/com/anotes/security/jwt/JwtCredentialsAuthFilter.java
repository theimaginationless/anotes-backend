package com.anotes.security.jwt;

import com.anotes.exception.BadRequestException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
public class JwtCredentialsAuthFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;
    private final AuthenticationManager authManager;
    private final JwtTokenService jwtTokenService;
    private final JwtProperties jwtProps;

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {
        JwtCredentials jwtCredentials = Try
                .of(() ->
                        this.objectMapper.readValue(request.getInputStream(), JwtCredentials.class)
                )
                .getOrElseThrow(() -> new BadRequestException("Cannot map request body to the entity"));
        Authentication toAuthenticate = new UsernamePasswordAuthenticationToken(
                jwtCredentials.getNickname(),
                jwtCredentials.getPassword()
        );
        Authentication authenticated = this.authManager.authenticate(toAuthenticate);

        return authenticated;
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) {
        String jwtToken = jwtTokenService.buildToken(authResult);
        response.addHeader(jwtProps.getHeader(), jwtProps.getTokenPrefix() + jwtToken);
    }
}
