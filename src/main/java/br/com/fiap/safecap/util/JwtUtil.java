package br.com.fiap.safecap.util;

import br.com.fiap.safecap.exception.JwtExpiredException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret:${JWT_SECRET:defaultSecretKey}}")
    private String jwtSecret;

    private final int jwtExpirationMs = 86400000;

    private SecretKey getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String email) {
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(getSigningKey(), SignatureAlgorithm.HS512)
            .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            throw new JwtExpiredException("Token expirado");
        } catch (JwtException e) {
            return false;
        }
    }
}
