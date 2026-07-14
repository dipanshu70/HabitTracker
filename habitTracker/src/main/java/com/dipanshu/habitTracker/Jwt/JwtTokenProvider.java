package com.dipanshu.habitTracker.Jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${jwt.secret:mySecretKeyForHabitTrackerApp12345}")
    private String jwtSecret;
    @Value("${jwt.expiration:86400000}")
    private long jwtExpirationMs;

    private SecretKey getSigningKey(){
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());

    }

    public String generateToken(String userId, String email) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
//    Is signature correct? (Has it been tampered with?) Is token expired?
//    (Has 24 hours passed?) Is format correct? (Does it look like a JWT?)
//     If ALL checks pass → return true If ANY check fails → return false
public boolean validateToken(String token) {
    try {
        // This single chain validates the signature, format, and expiration.
        Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token);

        // If the code reaches this line without throwing an exception,
        // the token is valid, correctly signed, and not expired.
        return true;

    } catch (io.jsonwebtoken.ExpiredJwtException e) {
        System.out.println("Token is expired: " + e.getMessage());
    } catch (io.jsonwebtoken.security.SecurityException | io.jsonwebtoken.MalformedJwtException e) {
        System.out.println("Invalid token format or tampered signature: " + e.getMessage());
    } catch (io.jsonwebtoken.UnsupportedJwtException e) {
        System.out.println("Unsupported token: " + e.getMessage());
    } catch (IllegalArgumentException e) {
        System.out.println("Token claims string is empty: " + e.getMessage());
    }

    return false;
}
    public String getUserIdFromToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

}
