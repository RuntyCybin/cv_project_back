package com.cybindev.persona.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cybindev.persona.domain.PersonaRequestDTO;
import com.cybindev.persona.domain.PersonaResponseDTO;
import com.cybindev.persona.service.PersonaService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;

@RestController
public class PersonaController {

  private final PersonaService<PersonaResponseDTO, PersonaRequestDTO> service;

  public PersonaController(PersonaService<PersonaResponseDTO, PersonaRequestDTO> service) {
    this.service = service;
  }

  @PostConstruct
  public void init() {
    System.out.println("Persona Controller initialized");
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
        .body("Persona controller is healthy");
  }
  /*
   * ------------------------------------------
   * !HEALTH CHECK
   * ------------------------------------------
   */

  /*
   * ------------------------------------------
   * POST PERSONA
   * ------------------------------------------
   */
  @PostMapping("/persona")
  @CircuitBreaker(name = "crearProyecto", fallbackMethod = "crearPersonaFallback")
  public ResponseEntity<PersonaResponseDTO> crearPersona(@RequestBody PersonaRequestDTO requestDTO) {
    PersonaResponseDTO responseDTO = this.service.crearPersona(requestDTO);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(responseDTO);
  }

  public ResponseEntity<PersonaResponseDTO> crearPersonaFallback(@RequestBody PersonaRequestDTO requestDTO,
      Throwable throwable) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            new PersonaResponseDTO(
                -1L,
                "Fallback Persona Post Persona",
                "Fallback Apellidos",
                LocalDate.now(),
                "0000000000",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "Fallback Nacionalidad"));
  }
  /*
   * ------------------------------------------
   * !POST PERSONA
   * ------------------------------------------
   */

  /*
   * ------------------------------------------
   * GET PERSONA POR ID
   * ------------------------------------------
   */
  @GetMapping("/persona/{id}")
  @CircuitBreaker(name = "obtenerPersonaPorId", fallbackMethod = "obtenerPersonaPorIdFallback")
  public ResponseEntity<PersonaResponseDTO> obtenerPersonaPorId(@PathVariable Long id) {
    PersonaResponseDTO responseDTO = this.service.obtenerPersonaPorId(id);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(responseDTO);
  }

  public ResponseEntity<PersonaResponseDTO> obtenerPersonaPorIdFallback(@PathVariable Long id, Throwable throwable) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(
            new PersonaResponseDTO(
                -1L,
                "Fallback Persona Get By Id",
                "Fallback Apellidos",
                LocalDate.now(),
                "0000000000",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "",
                "Fallback Nacionalidad"));
  }
  /*
   * ------------------------------------------
   * !GET PERSONA POR ID
   * ------------------------------------------
   */

  /*
   * ------------------------------------------
   * GET PERSONAS
   * ------------------------------------------
   */
  @GetMapping("/all-personas")
  @CircuitBreaker(name = "listarPersona", fallbackMethod = "listarPersonaFallback")
  public ResponseEntity<List<PersonaResponseDTO>> listarPersona() {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(this.service.listarPersona());
  }

  public ResponseEntity<List<PersonaResponseDTO>> listarPersonaFallback(Throwable throwable) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(List.of(new PersonaResponseDTO(
            -1L,
            "Fallback Persona Get all Personas",
            "Fallback Apellidos",
            LocalDate.now(),
            "0000000000",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "Fallback Nacionalidad")));
  }
  /*
   * ------------------------------------------
   * !GET PERSONAS
   * ------------------------------------------
   */
}
