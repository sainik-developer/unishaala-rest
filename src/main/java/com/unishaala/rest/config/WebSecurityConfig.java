package com.unishaala.rest.config;

import com.unishaala.rest.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Base class for web security. This class sets adds the authentication providers, which get called
 * in Filters to authenticate based on jwt tokens. It also sets the unauthorized entry point and
 * other default security settings
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@ComponentScan(basePackages = "com.unishaala.rest")
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final List<AuthenticationProvider> authenticationProviders;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(final AuthenticationManagerBuilder builder) throws Exception {
        authenticationProviders.forEach(builder::authenticationProvider);
    }

    private OncePerRequestFilter getJwtAuthFilter() throws Exception {
        return new JwtAuthFilter(authenticationManagerBean());
    }

    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity.anonymous().disable()
                // CSRF ? what is it ?
                .csrf().disable()
                .rememberMe().disable()
                // REST comes with request cache not required
                .requestCache().disable()
                // auth entry points are open to bypass jwt authentication
                .authorizeRequests().antMatchers("/auth/").permitAll()
                // rest all request are authenticated
                .anyRequest().authenticated()
                .and()
                // how to handle the unauthenticated request
                .exceptionHandling().authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))
                .and()
                // state less service for REST end points
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.addFilterBefore(getJwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
    }

}
