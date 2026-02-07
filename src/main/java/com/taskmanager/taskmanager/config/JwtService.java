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

  public String extractUsername(String token){
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

  public String generatorAccessToken(Map<String, Objects> claims, UserDetails userDetails){
      Long expirationAccessToken = jwtSecurityConfigProperties.getAccessToken().getExpiration();
      return buildToken(claims,userDetails,expirationAccessToken);
  }

  public String generatorRefreshToken(Map<String, Objects> claims, UserDetails userDetails){
      Long expirationRefreshToken = jwtSecurityConfigProperties.getRefreshToken().getExpiration();
      return buildToken(claims,userDetails,expirationRefreshToken);
  }


  public Date extractExpiration(String token){
      return extractClaims(token, Claims::getExpiration);
  }


  private boolean isTokenExpired(String token){
      return extractExpiration(token).before(new Date());
  }


  public boolean isTokenValid(String token, UserDetails userDetails){
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }
}
