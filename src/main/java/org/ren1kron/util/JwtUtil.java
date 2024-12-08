// org.ren1kron.util.JwtUtil.java
package org.ren1kron.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    // Secret key should be at least 256 bits for HS256
    private static final String SECRET_KEY = "3f21b7d4a9c64e3e9c1b8f7123d4a67e4a55b9f48c7d1e8eaf214e9f8c5b3a4f";
    private static final long EXPIRATION_TIME = 86400000; // 1 day in milliseconds

    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Generate JWT Token
    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuer("web_lab4")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validate and parse JWT Token
    public static Jws<Claims> validateToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    // Extract username from JWT Token
    public static String extractUsername(String token) {
        return validateToken(token).getBody().getSubject();
    }
}
