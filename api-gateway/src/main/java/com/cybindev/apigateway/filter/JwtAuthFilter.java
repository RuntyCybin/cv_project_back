package com.cybindev.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.List;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

  private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);

  // Rutas que no requieren token
  private static final List<String> RUTAS_PUBLICAS = List.of(
      "/cv/autenticacion",
      "/cv/autenticacion/login");

  @Value("${app.jwt.secret}")
  private String secret;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    logger.info("API-GATEWAY-INFO: Filtrando solicitud para la ruta: {}", exchange.getRequest().getURI().getPath());
    String path = exchange.getRequest().getURI().getPath();

    if (esRutaPublica(path)) {
      logger.info("API-GATEWAY-INFO: Ruta es pública, permitiendo acceso: {}", path);
      return chain.filter(exchange);
    }

    String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      logger.warn("API-GATEWAY-WARN: Acceso denegado, token no proporcionado o inválido: {}", path);

      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }

    String token = authHeader.substring(7);

    if (!esValido(token)) {
      logger.warn("API-GATEWAY-WARN: Acceso denegado, token inválido: {}", path);
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }
    return chain.filter(exchange);
  }

  private boolean esRutaPublica(String path) {
    logger.info("API-GATEWAY-INFO: Verificando si la ruta es pública: {}", path);
    return RUTAS_PUBLICAS.stream().anyMatch(path::startsWith);
  }

  private boolean esValido(String token) {
    try {
      SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
      Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
      return true;
    } catch (Exception e) {
      logger.error("API-GATEWAY-EXCEPTION: " + e.getMessage());
      return false;
    }
  }

  @Override
  public int getOrder() {
    return -1; // se ejecuta antes que cualquier otro filtro
  }
}
