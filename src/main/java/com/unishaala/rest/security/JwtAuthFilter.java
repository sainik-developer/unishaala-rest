package com.unishaala.rest.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;
    private final String BEARER_TOKEN = "Bearer";

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        final String bearerToken = getToken(httpServletRequest);
        if (bearerToken != null && bearerToken.startsWith(BEARER_TOKEN)) {
            // Strip authenticationType from string. Do it so auth tokens are consistently passed authorization as credentials
            final String jwToken = bearerToken.substring(BEARER_TOKEN.length()).trim();
            final Authentication token = new JwtAuthenticationToken(jwToken);
            try {
                // set authenticated object on the security context. AuthResult contains principal and authorities
                SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(token));
            } catch (final Exception e) {
                // No need to throw exception here. Let normal filter process continue since not having authenticated
                // result in security context will return appropriate result to client
                log.warn("Unable to authenticate for Schema: {} with exception {}", BEARER_TOKEN, e);
            }
        }
    }

    private String getToken(final HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
    }
}
