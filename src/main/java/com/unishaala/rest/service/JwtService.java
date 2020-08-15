package com.unishaala.rest.service;

import com.unishaala.rest.enums.UserType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {
    @Value("${jwt.key}")
    private String JWT_SIGN_KEY;
    @Value("${jwt.admin.timeout.days}")
    private int JWT_ADMIN_EXPIRATION;
    @Value("${jwt.user.timeout.days}")
    private int JWT_USER_EXPIRATION;
    @Value("${jwt.teacher.timeout.days}")
    private int JWT_TEACHER_EXPIRATION;

    public final static String JWT_CLAIM_ID = "id";
    public final static String JWT_CLAIM_ROLE = "role";

    public String createToken(final Claims claims, final UserType userType) {
        SecretKey key = Keys.hmacShaKeyFor(JWT_SIGN_KEY.getBytes());
        return Jwts.builder().signWith(key)
                .setExpiration(createTimeout(userType))
                .setClaims(claims).compact();
    }

    /***
     * It will verify the properly signed snd unmodified as well expiration time is not yet passed.
     *
     * @param token
     * @return
     */
    public boolean isValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(JWT_SIGN_KEY.getBytes()).build().parseClaimsJws(token);
        } catch (JwtException e) {
            return false;
        }
        return true;
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(JWT_SIGN_KEY.getBytes()).build().parseClaimsJws(token).getBody();
    }

    public Claims userClaims(final UUID userId, final UserType userType) {
        Claims claims = Jwts.claims();
        claims.put(JWT_CLAIM_ID, userId);
        claims.put(JWT_CLAIM_ROLE, userType);
        return claims;
    }

    private Date createTimeout(final UserType userType) {
        switch (userType) {
            case ADMIN:
                return DateUtils.addDays(new Date(), JWT_ADMIN_EXPIRATION);
            case STUDENT:
                return DateUtils.addDays(new Date(), JWT_USER_EXPIRATION);
            case TEACHER:
                return DateUtils.addDays(new Date(), JWT_TEACHER_EXPIRATION);
            default:
                return new Date();
        }
    }
}
