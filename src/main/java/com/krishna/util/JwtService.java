package com.krishna.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "MySecretKeyForStudentManagementSystem123456789";

    private final SecretKey key =
            Keys.hmacShaKeyFor(
                    SECRET_KEY.getBytes(
                            StandardCharsets.UTF_8
                    )
            );

    // ============================
    // Generate token
    // ============================

    public String generateToken(
            UserDetails userDetails) {

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 1000L * 60 * 60
                        )
                )
                .signWith(key)
                .compact();
    }

    // ============================
    // Extract username
    // ============================

    public String extractUsername(
            String token) {

        return extractClaim(
                token,
                Claims::getSubject
        );
    }

    // ============================
    // Extract claim
    // ============================

    private <T> T extractClaim(
            String token,
            Function<Claims, T> claimsResolver) {

        Claims claims =
                Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

        return claimsResolver.apply(claims);
    }

    // ============================
    // Validate token
    // ============================

    public boolean isTokenValid(
            String token,
            UserDetails userDetails) {

        try {

            String username =
                    extractUsername(token);

            return username.equals(
                    userDetails.getUsername()
            ) && !isTokenExpired(token);

        } catch (Exception e) {

            return false;
        }
    }

    // ============================
    // Check expiration
    // ============================

    private boolean isTokenExpired(
            String token) {

        Date expiration =
                extractClaim(
                        token,
                        Claims::getExpiration
                );

        return expiration.before(
                new Date()
        );
    }
}