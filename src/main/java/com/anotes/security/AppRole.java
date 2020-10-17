package com.anotes.security;

import io.vavr.collection.Stream;
import io.vavr.control.Option;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
public enum AppRole {
    ADMIN(
            Stream.of(
                    AppAuthority.USER_READ,
                    AppAuthority.USER_WRITE,
                    AppAuthority.ADMIN_READ,
                    AppAuthority.ADMIN_WRITE
            )
    ),
    USER(
            Stream.of(
                    AppAuthority.USER_READ,
                    AppAuthority.USER_WRITE
            )
    );

    private static final String ROLE_PREFIX = "ROLE_";

    private Stream<AppAuthority> authorities;

    AppRole(Stream<AppAuthority> authorities) {
        this.authorities = authorities;
    }

    public Set<String> getGrantedAuthorities() {
        return this.authorities
                .map(Enum::name)
                .append(ROLE_PREFIX + this.name()) // add role as authority
                .collect(Collectors.toSet());
    }

    public static Option<AppRole> from(Set<String> authorities) {
        return Stream.ofAll(authorities)
                .find(auth -> auth.startsWith(AppRole.ROLE_PREFIX))
                .map(auth -> auth.replace(AppRole.ROLE_PREFIX, ""))
                .map(AppRole::valueOf);
    }
}