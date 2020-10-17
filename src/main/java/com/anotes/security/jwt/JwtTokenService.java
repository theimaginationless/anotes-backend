package com.anotes.security.jwt;

import com.anotes.entity.User;
import com.anotes.exception.BadRequestException;
import com.anotes.security.AppUser;
import com.anotes.service.UserService;
import com.anotes.util.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class JwtTokenService {

    private final JwtProperties jwtProps;
    private final UserService userService;
    private final WebAuthenticationDetailsSource webAuthDetailsSource = new WebAuthenticationDetailsSource();

    public String buildToken(Authentication authentication) {
        LocalDateTime now = LocalDateTime.now();

        String jwtToken = Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(Utils.localDateTime2Date(now))
                .setExpiration(Utils.localDateTime2Date(now.plusDays(jwtProps.getTokenExpirationAfterDays())))
                .signWith(getSecretKey())
                .compact();

        return jwtToken;
    }

    public Authentication parseToken(HttpServletRequest request) {
        String authHeader = request.getHeader(jwtProps.getHeader());

        if (StringUtils.isEmpty(authHeader)) {
            throw new BadRequestException("No JWT token");
        } else if (!authHeader.startsWith(jwtProps.getTokenPrefix())) {
            throw new BadRequestException(String.format("JWT token must starts with %s", jwtProps.getTokenPrefix()));
        }

        String jwtToken = authHeader.replace(jwtProps.getTokenPrefix(), "");

        UsernamePasswordAuthenticationToken auth = Try
                .of(() -> {
                    Jws<Claims> claims = Jwts.parserBuilder()
                            .setSigningKey(getSecretKey())
                            .build()
                            .parseClaimsJws(jwtToken);
                    Claims claimsBody = claims.getBody();
                    String nickname = claimsBody.getSubject();

                    User user = userService.findByNickname(nickname)
                            .getOrElseThrow(() -> new BadRequestException("Bad user nickname in JWT token"));
                    AppUser appUser = new AppUser(user);

                    return new UsernamePasswordAuthenticationToken(
                            appUser,
                            null,
                            appUser.getAuthorities()
                    );
                })
                .getOrElseThrow(BadRequestException::new);
        auth.setDetails(webAuthDetailsSource.buildDetails(request));

        return auth;
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtProps.getSecretKey().getBytes());
    }
}
