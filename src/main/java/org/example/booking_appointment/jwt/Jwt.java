package org.example.booking_appointment.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.example.booking_appointment.enums.ROLE;

import javax.crypto.SecretKey;
import java.util.Date;

public class Jwt {

    private final Claims claims;
    private final SecretKey secretKey;

    public Jwt(Claims claims, SecretKey secretKey) {
        this.claims = claims;
        this.secretKey = secretKey;
    }

    public boolean isExpired() {
        return claims.getExpiration().before(new Date());
    }

    public Long getProfileId() {
        return Long.valueOf(claims.getSubject());
    }

    public ROLE getRole() {
        return ROLE.valueOf(claims.get("role", String.class));
    }

    public String toString() {
        return Jwts.builder().claims(claims).signWith(secretKey).compact();
    }
}
