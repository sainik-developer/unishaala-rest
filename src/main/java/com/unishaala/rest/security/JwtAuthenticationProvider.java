package com.unishaala.rest.security;

import com.unishaala.rest.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.awt.image.ImageProducer;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;

/***
 * This is the redis bearer token verification provider, It check if token is presents in redis if so then
 * it authenticate the call and allows the request to go to request end point
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtUtils jwtUtils;

    // JWT support from unishaala-rest
    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        return Optional.of(authentication instanceof JwtAuthenticationToken)
                .filter(aBoolean -> aBoolean && jwtUtils.isValid((String) authentication.getCredentials()))
                .map(aBoolean -> jwtUtils.getClaims((String) authentication.getCredentials()))
                .filter(claims -> Objects.nonNull(claims.get(JwtUtils.JWT_CLAIM_ID)) && Objects.nonNull(claims.get(JwtUtils.JWT_CLAIM_ROLE)))
                .map(claims -> new JwtAuthenticationToken(new AuthUserDetails((String) claims.get(JwtUtils.JWT_CLAIM_ID)), (String) authentication.getCredentials(), Collections.singletonList(new SimpleGrantedAuthority((String) claims.get(JwtUtils.JWT_CLAIM_ROLE)))))
                .orElse(null);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return JwtAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
