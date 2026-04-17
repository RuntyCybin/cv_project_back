package com.cybindev.autenticacion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cybindev.autenticacion.domain.AutenticacionResponseDTO;
import com.cybindev.autenticacion.domain.PersonaAutenticacionRequestDTO;
import com.cybindev.autenticacion.domain.PersonaAutenticacionResponseDTO;
import com.cybindev.autenticacion.service.PersonaAutenticacionService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/persona-autenticacion")
public class PersonaAutenticacionController {

  private final PersonaAutenticacionService<PersonaAutenticacionResponseDTO, PersonaAutenticacionRequestDTO> personaAutenticacionService;

  public PersonaAutenticacionController(
      PersonaAutenticacionService<PersonaAutenticacionResponseDTO, PersonaAutenticacionRequestDTO> personaAutenticacionService) {
    this.personaAutenticacionService = personaAutenticacionService;
  }

  @PostConstruct
  public void init() {
    System.out.println("Persona - Autenticacion Controller initialized");
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
        .body("Persona - Autenticacion controller is healthy");
  }
  /*
   * ------------------------------------------
   * !HEALTH CHECK
   * ------------------------------------------
   */

  /*
   * ------------------------------------------
   * POST PERSONA - AUTENTICACION
   * ------------------------------------------
   */
  @PostMapping
  @CircuitBreaker(name = "crearPersonaAutenticacion", fallbackMethod = "crearPersonaAutenticacionFallback")
  public ResponseEntity<PersonaAutenticacionResponseDTO> crearPersonaAutenticacion(
      @RequestBody PersonaAutenticacionRequestDTO requestDTO) {
    PersonaAutenticacionResponseDTO responseDTO = this.personaAutenticacionService
        .crearPersonaAutenticacion(requestDTO);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(responseDTO);
  }

  public ResponseEntity<PersonaAutenticacionResponseDTO> crearPersonaAutenticacionFallback(
      @RequestBody PersonaAutenticacionRequestDTO requestDTO, Throwable throwable) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new PersonaAutenticacionResponseDTO(-1L, -1L, -1L));
  }
  /*
   * ------------------------------------------
   * !POST PERSONA - AUTENTICACION
   * ------------------------------------------
   */

  /*
   * ------------------------------------------
   * GET PERSONA - AUTENTICACION POR ID AUTENTICACION
   * ------------------------------------------
   */
  @GetMapping("/{idautenticacion}")
  @CircuitBreaker(name = "getPersonaAutenticacionPorIdAutenticacion", fallbackMethod = "getPersonaAutPorIdAutFallback")
  public ResponseEntity<PersonaAutenticacionResponseDTO> obtenerPersonaAuthPorIdAuth(
      @PathVariable Long idautenticacion) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(this.personaAutenticacionService.obtenerPersonaAutenticacionPorAutenticacionId(idautenticacion));
  }

  public ResponseEntity<PersonaAutenticacionResponseDTO> getPersonaAutPorIdAutFallback(
      @RequestBody PersonaAutenticacionRequestDTO requestDTO, Throwable throwable) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new PersonaAutenticacionResponseDTO(-1L, -1L, -1L));
  }
  /*
   * ------------------------------------------
   * !GET PERSONA - AUTENTICACION POR ID AUTENTICACION
   * ------------------------------------------
   */
}
