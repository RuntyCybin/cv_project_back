package com.cybindev.autenticacion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cybindev.autenticacion.domain.AutenticacionRequestDTO;
import com.cybindev.autenticacion.domain.AutenticacionResponseDTO;
import com.cybindev.autenticacion.service.AutenticacionService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/autenticacion")
public class AutenticacionController {

  private final AutenticacionService<AutenticacionResponseDTO, AutenticacionRequestDTO> service;

  public AutenticacionController(AutenticacionService<AutenticacionResponseDTO, AutenticacionRequestDTO> service) {
    this.service = service;
  }

  @PostConstruct
  public void init() {
    System.out.println("Autenticacion Controller initialized");
  }

  /*
   * ------------------------------------------
   * HEALTH CHECK
   * ------------------------------------------
   */
  @GetMapping
  public ResponseEntity<String> health() {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body("Autenticacion controller is healthy");
  }
  /*
   * ------------------------------------------
   * !HEALTH CHECK
   * ------------------------------------------
   */

  /*
   * ------------------------------------------
   * POST AUTENTICACION
   * ------------------------------------------
   */
  @PostMapping
  @CircuitBreaker(name = "crearAutenticacion", fallbackMethod = "crearAutenticacionFallback")
  public ResponseEntity<AutenticacionResponseDTO> crearAutenticacion(@RequestBody AutenticacionRequestDTO requestDTO) {
    AutenticacionResponseDTO responseDTO = this.service.crearAutenticacion(requestDTO);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(responseDTO);
  }

  public ResponseEntity<AutenticacionResponseDTO> crearAutenticacionFallback(
      @RequestBody AutenticacionRequestDTO requestDTO, Throwable throwable) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new AutenticacionResponseDTO(-1L, "Fallback Login", "Fallback Password"));
  }
  /*
   * ------------------------------------------
   * !POST AUTENTICACION
   * ------------------------------------------
   */

  /*
   * ------------------------------------------
   * GET AUTENTICACION POR ID
   * ------------------------------------------
   */
  @GetMapping("/{id}")
  @CircuitBreaker(name = "obtenerAutenticacionPorId", fallbackMethod = "obtenerAutenticacionPorIdFallback")
  public ResponseEntity<AutenticacionResponseDTO> obtenerAutenticacionPorId(@PathVariable Long id) {
    AutenticacionResponseDTO responseDTO = this.service.obtenerAutenticacionPorId(id);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(responseDTO);
  }

  public ResponseEntity<AutenticacionResponseDTO> obtenerAutenticacionPorIdFallback(@PathVariable Long id,
      Throwable throwable) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new AutenticacionResponseDTO(-1L, "Fallback Login", "Fallback Password"));
  }
  /*
   * ------------------------------------------
   * !GET AUTENTICACION POR ID
   * ------------------------------------------
   */

  /*
   * ------------------------------------------
   * GET AUTENTICACION POR LOGIN
   * ------------------------------------------
   */
  @GetMapping("/login/{login}")
  @CircuitBreaker(name = "obtenerAutenticacionPorLogin", fallbackMethod = "obtenerAutenticacionPorLoginFallback")
  public ResponseEntity<AutenticacionResponseDTO> obtenerAutenticacionPorLogin(@PathVariable String login) {
    AutenticacionResponseDTO responseDTO = this.service.obtenerAutenticacionPorLogin(login);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(responseDTO);
  }

  public ResponseEntity<AutenticacionResponseDTO> obtenerAutenticacionPorLoginFallback(@PathVariable String login,
      Throwable throwable) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new AutenticacionResponseDTO(-1L, "Fallback Login", "Fallback Password"));
  }
  /*
   * ------------------------------------------
   * !GET AUTENTICACION POR LOGIN
   * ------------------------------------------
   */

  /*
   * ------------------------------------------
   * POST AUTENTICACION LOGIN
   * ------------------------------------------
   */
  @PostMapping("/login")
  @CircuitBreaker(name = "obtenerAutenticacionPorLoginYPassword", fallbackMethod = "obtenerAutenticacionPorLoginYPasswordFallback")
  public ResponseEntity<AutenticacionResponseDTO> obtenerAutenticacionPorLoginYPassword(
      @RequestBody AutenticacionRequestDTO requestDTO) {
    AutenticacionResponseDTO responseDTO = this.service.obtenerAutenticacionPorLoginYPassword(requestDTO);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(responseDTO);
  }

  public ResponseEntity<AutenticacionResponseDTO> obtenerAutenticacionPorLoginYPasswordFallback(
      @RequestBody AutenticacionRequestDTO requestDTO, Throwable throwable) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new AutenticacionResponseDTO(-1L, "Fallback Login", "Fallback Password"));
  }
  /*
   * ------------------------------------------
   * !POST AUTENTICACION LOGIN
   * ------------------------------------------
   */
}
