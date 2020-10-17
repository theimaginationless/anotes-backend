package com.anotes.util;

import com.anotes.entity.User;
import com.anotes.exception.BadRequestException;
import com.anotes.security.AppAuthority;
import com.anotes.security.AppUser;
import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import io.vavr.control.Try;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Date;

public class Utils {

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
}
