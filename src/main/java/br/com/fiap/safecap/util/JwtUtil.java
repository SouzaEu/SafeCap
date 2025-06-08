package br.com.fiap.safecap.util;

import br.com.fiap.safecap.exception.JwtExpiredException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Profile("!nodb")
@Component
public class JwtUtil {

    private final SecretKey signingKey;
    private final int jwtExpirationMs = 86400000;

    public JwtUtil() {
        // Gerar uma chave segura de 512 bits para HS512 apenas uma vez
        this.signingKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    public String generateToken(String email) {
        return Jwts.builder()
            .setSubject(email)
            .setIssuedAt(new Date())
            .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
            .signWith(signingKey, SignatureAlgorithm.HS512)
            .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(signingKey)
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
