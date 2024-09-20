package com.blogpessoal.blogpessoaljava.security;

import java.security.Key;
import java.util.function.Function;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
  public static final String SECRET = "9958e8bc4112ee6b92e6150cdf0d075a456b420fa596c6e7649e8e61c95951cb\r\n" + //
        "4fb360126fd039227d392c20f1b958eabbd4a0a4b66d1bb793463a08010ba9a3\r\n" + //
        "73563808e79dece9247aca5a50c66bca37ac8633cf7a66d097bcb5c939a06928\r\n" + //
        "c85c64cb9608ea3a277650b127f2284c73c6528afc24596e820293ef9ef2320b\r\n" + //
        "56faf062df5a21318fd3c182c388a5c91365cab36261d24ffd805e8d3da8e6c7\r\n" + //
        "b5d293cfda039f08be7a5c248f82bc11ab87ca8a768d1daea4e7333db690ccce\r\n" + //
        "6bc37da7cefd60ff3ec51efbddb57e38d846b06c64a598deead5e823825372a5";

  private Key getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Boolean validateToken(String token, UserDetails UserDetails) {
    final String username = extractUsername(token);
    return (username.equals(UserDetails.getUsername()) && !isTokenExpired(token));
  }

  private String createToken(Map<String, Object> claims, String username) {

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
        .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
  }

  public String generateToken(String username) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, username);
  }

}
