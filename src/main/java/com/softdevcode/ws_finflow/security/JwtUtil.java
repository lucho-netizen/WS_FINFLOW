package com.softdevcode.ws_finflow.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Clave secreta segura (32+ caracteres)
    private final String SECRET_KEY = "MiClaveSuperSecretaMuyLargaDeAlMenos32Chars!";

    private final long EXPIRATION = 1000 * 60 * 60 * 5; // 5 horas

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    // Generar token
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Validar token y extraer email
    public String validateTokenAndGetEmail(String token) throws Exception {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims.getSubject(); // este es el email
        } catch (Exception e) {
            throw new Exception("Token inválido o expirado");
        }
    }
}