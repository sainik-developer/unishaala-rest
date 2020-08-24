package com.unishaala.rest.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
public final class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String credentials;
    private final UserDetails principal;

    public JwtAuthenticationToken(String token) {
        super(null);
        credentials = token;
        principal = null;
    }

    public void setAuthenticated(final boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    public JwtAuthenticationToken(final UserDetails userDetails, final String token,
                                  final Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        principal = userDetails;
        credentials = token;
        super.setAuthenticated(true); // must use super, as we override
    }

}
