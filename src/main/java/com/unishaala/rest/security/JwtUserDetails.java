package com.unishaala.rest.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
public class JwtUserDetails implements UserDetails {

    private String username;
    private String token;
    private Collection<? extends GrantedAuthority> authorities;
    private String password = null;
    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = true;

    public JwtUserDetails(String userName, String token, List<GrantedAuthority> grantedAuthorities) {
        this.username = userName;
        this.token = token;
        this.authorities = grantedAuthorities;
    }
}
