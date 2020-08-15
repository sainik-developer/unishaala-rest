package com.unishaala.rest.security;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
public final class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String credentials;
    private final UserDetails principal;

    /**
     * @param token is the JWT token
     */
    public JwtAuthenticationToken(final String token) {
        super(null);
        this.credentials = token;
        this.principal = null;
        setAuthenticated(false);
    }

    /***
     *
     * @param userDetails
     * @param token
     * @param authorities
     */
    public JwtAuthenticationToken(final UserDetails userDetails, final String token,
                                  final Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = userDetails;
        this.credentials = token;
        super.setAuthenticated(true); // must use super, as we override
    }

    public void setAuthenticated(final boolean isAuthenticated) throws IllegalArgumentException {
        // TODO write why
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }
}
