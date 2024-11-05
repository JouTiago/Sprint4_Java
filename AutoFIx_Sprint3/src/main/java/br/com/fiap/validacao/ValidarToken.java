package br.com.fiap.validacao;

import io.jsonwebtoken.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ValidarToken {
    private static final String SECRET_KEY = "teste-chave";
    private static final long EXPIRATION_TIME = 43200000;

    // Gerar token JWT
    public static String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .compact();
    }

    // Validar token JWT
    public static boolean validateToken(String token, String userId) {
        final String extractedUserId = extractUserId(token);
        return (extractedUserId.equals(userId) && !isTokenExpired(token));
    }

    // Extrair userId do token
    public static String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Verificar se o token está expirado
    private static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Extrair data de expiração
    private static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Extrair qualquer claim do token
    private static <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extrair todas as claims
    private static Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
