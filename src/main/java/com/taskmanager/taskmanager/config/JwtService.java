package com.taskmanager.taskmanager.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtService {

    private final JwtSecurityConfigProperties jwtSecurityConfigProperties;


    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecurityConfigProperties.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
