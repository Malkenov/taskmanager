package com.taskmanager.taskmanager.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtService {

    private final JwtSecurityConfigProperties jwtSecurityConfigProperties;


  public SecretKey getSignInKey(){
      byte[] keyByte = Decoders.BASE64.decode(jwtSecurityConfigProperties.getSecretKey());
      return Keys.hmacShaKeyFor(keyByte);
  }


  public Claims getAllClaimsFromToken(String token){
      return Jwts.parserBuilder()
              .setSigningKey(getSignInKey())
              .build()
              .parseClaimsJws(token)
              .getBody();
  }


  public <T> T extractClaims(String token,Function<Claims, T> claimsTFunction){
      final Claims claims = getAllClaimsFromToken(token);
      return claimsTFunction.apply(claims);
  }

  public String getUsername(String token){
      return extractClaims(token, Claims::getSubject);
  }

  public String buildToken(Map<String, Objects> claims, UserDetails userDetails, Long expiration){
      return Jwts.builder()
              .setClaims(claims)
              .setSubject(userDetails.getUsername())
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() + expiration))
              .signWith(getSignInKey(), SignatureAlgorithm.HS256)
              .compact();
  }
}
