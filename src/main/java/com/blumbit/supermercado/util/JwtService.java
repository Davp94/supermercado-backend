package com.blumbit.supermercado.util;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.blumbit.supermercado.entity.Usuario;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;

    public String extractUsername(String token){
        return Jwts.parser()
                .verifyWith(getSigningkey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public String generateToken(Usuario usuario){
        return buildToken(usuario, jwtExpiration);
    }

    public String generateRefreshToken(Usuario usuario){
         return buildToken(usuario, refreshExpiration);
    }

    private String buildToken(Usuario usuario, long expiration){
        return Jwts.builder()
                    .claims(Map.of("nombres", usuario.getNombres()))
                    .subject(usuario.getEmail())
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis()+expiration))
                    .signWith(getSigningkey())
                    .compact();
    }

    public boolean isTokenValid(String token, Usuario usuario){
        String username = extractUsername(token);
        return (username.equals(usuario.getEmail())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return Jwts.parser()
                .verifyWith(getSigningkey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    private SecretKey getSigningkey(){
        final byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
