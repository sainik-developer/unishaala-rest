package com.unishaala.rest.security;

import com.unishaala.rest.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

/***
 * This is the redis bearer token verification provider, It check if token is presents in redis if so then
 * it authenticate the call and allows the request to go to request end point
 */
@Log4j2
@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {
    private final JwtService jwtService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        final String token = jwtAuthenticationToken.getCredentials();
        if (!jwtService.isValid(token)) {
            throw new BadCredentialsException("Invalid token!");
        }
        final Claims claims = jwtService.getClaims(token);
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList((String) claims.get(JwtService.JWT_CLAIM_ROLE));
        return new JwtAuthenticationToken(new JwtUserDetails((String) claims.get(JwtService.JWT_CLAIM_ID), token, grantedAuthorities), token, grantedAuthorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return JwtAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
