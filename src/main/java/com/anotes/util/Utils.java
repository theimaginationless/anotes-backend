package com.anotes.util;

import com.anotes.entity.User;
import com.anotes.exception.BadRequestException;
import com.anotes.security.AppAuthority;
import com.anotes.security.AppUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import io.vavr.control.Try;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Utils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static AppUser castAuthToAppUser(Authentication authentication) {
        return Try.of(() -> (AppUser) authentication.getPrincipal())
                .getOrElseThrow(() -> new IllegalArgumentException("Cannot cast auth.principal to AppUser"));
    }

    public static Boolean hasAppAuthority(Authentication authentication, AppAuthority appAuthority) {
        AppUser appUser = castAuthToAppUser(authentication);
        Set<String> knownAuthorities = HashSet.of(AppAuthority.values()).map(Enum::name);

        return Stream.ofAll(appUser.getAuthorities())
                .map(GrantedAuthority::getAuthority)
                .filter(knownAuthorities::contains)
                .map(AppAuthority::valueOf)
                .exists(appAuthority::equals);
    }

    /**
     * Throws an exception if the authenticated user is not an owner.
     */
    public static void checkOwnership(Authentication authentication, User owner) {
        AppUser appUser = Utils.castAuthToAppUser(authentication);
        Long ownerId = owner.getId();
        Long appUserId = appUser.getUser().getId();

        if (!ownerId.equals(appUserId)) {
            throw new BadRequestException(
                    String.format("Access denied because of user id=%d is not an owner", appUserId)
            );
        }
    }

    /**
     * Throws an exception if the authenticated user is not admin or owner.
     */
    public static void checkIsAdminOrOwner(Authentication authentication, User owner) {
        Boolean hasAdminWritePerms = Utils.hasAppAuthority(authentication, AppAuthority.ADMIN_WRITE);
        if (!hasAdminWritePerms) {
            Utils.checkOwnership(authentication, owner);
        }
    }

    public static LocalDateTime date2LocalDateTime(Date dateToConv) {
        return dateToConv.toInstant()
                .atZone(Constants.DEFAULT_ZONE)
                .toLocalDateTime();
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(Constants.DEFAULT_ZONE).toInstant());
    }

    public static <T> T readRequestBody(HttpServletRequest request, Class<T> mapperClass) {
        return Try
                .of(() ->
                        objectMapper.readValue(request.getInputStream(), mapperClass)
                )
                .getOrElseThrow(() -> new BadRequestException("Cannot map request body to the entity"));
    }

    public static Map<String, Object> buildErrorBody(
            HttpStatus status,
            List<String> messages
    ) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("errors", messages);

        return body;
    }
}
