package com.anotes.security;

import com.anotes.entity.User;
import com.anotes.security.jwt.JwtProperties;
import com.anotes.security.jwt.JwtTokenService;
import com.anotes.service.UserService;
import com.anotes.util.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class RegistrationFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenService jwtTokenService;
    private final JwtProperties jwtProps;
    private final ObjectMapper objectMapper;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        RegistrationRequest regRequest = Utils.readRequestBody(request, RegistrationRequest.class);

        // Validate request
        Set<ConstraintViolation<RegistrationRequest>> violations = validator.validate(regRequest);
        if (!violations.isEmpty()) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            List<String> errors = violations.stream()
                    .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                    .collect(Collectors.toList());
            String errorsStr = errors.stream().collect(Collectors.joining(",", "[", "]"));
            Map<String, Object> respBody = Utils.buildErrorBody(status, errors);

            log.error("Constraint violation, errors={}", errorsStr);
            prepareErrorResponse(response, status, respBody);
            return;
        }

        // Check is user nickname is already taken
        String nickname = regRequest.getNickname();
        Boolean isUserExists = userService.findByNickname(nickname).isDefined();
        if (isUserExists) {
            HttpStatus status = HttpStatus.BAD_REQUEST;
            String message = String.format("Nickname '%s' is already taken", nickname);
            Map<String, Object> respBody = Utils.buildErrorBody(status, List.of(message));

            log.error(message);
            prepareErrorResponse(response, status, respBody);
            return;
        }

        // Create a new user
        User user = new User(
                regRequest.getNickname(),
                regRequest.getFullName(),
                passwordEncoder.encode(regRequest.getPassword()),
                AppRole.USER.getGrantedAuthorities(),
                true
        );
        user = userService.save(user);
        log.info("Registered a new user={}", user);

        // Authenticate user
        AppUser appUser = new AppUser(user);
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                appUser,
                null,
                appUser.getAuthorities()
        );

        // Return JWT token
        String jwtToken = jwtTokenService.buildToken(auth);
        response.addHeader(jwtProps.getHeader(), jwtProps.getTokenPrefix() + jwtToken);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        if (!HttpMethod.POST.matches(request.getMethod())) {
            return true;
        }
        String path = request.getServletPath();
        path = path.endsWith("/") ? String.join("/", path.split("/")) : path; // remove trailing '/'
        return !path.equals("/register");
    }

    private void prepareErrorResponse(
            HttpServletResponse response,
            HttpStatus errorCode,
            Map<String, Object> respBody
    ) throws IOException {
        response.setStatus(errorCode.value());
        response.setHeader("Content-Type", "application/json");

        String json = Try.of(() -> objectMapper.writeValueAsString(respBody))
                .getOrElseThrow(t -> new RuntimeException(t));
        response.getWriter().print(json);
    }
}
