package com.cybindev.autenticacion.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  @Value("${app.jwt.secret}")
  private String secret;

  @Value("${app.jwt.expiration}")
  private long expirationSeconds;

  private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

  public String generarToken(String login, Long id) {
    SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
    Date now = new Date();
    Date expiration = new Date(now.getTime() + expirationSeconds * 1000);

    return Jwts.builder()
        .subject(login)
        .claim("id", id)
        .issuedAt(now)
        .expiration(expiration)
        .signWith(key)
        .compact();
  }

  public String extraerLogin(String token) {
    SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
    return Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
  }

  public boolean esValido(String token) {
    try {
      SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
      Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

}
