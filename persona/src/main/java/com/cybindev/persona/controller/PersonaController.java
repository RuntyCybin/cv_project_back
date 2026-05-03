package com.cybindev.persona.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybindev.persona.domain.PersonaRequestDTO;
import com.cybindev.persona.domain.PersonaResponseDTO;
import com.cybindev.persona.service.PersonaService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/persona")
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
   * POST PERSONA
   * ------------------------------------------
   */
  @PostMapping
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
                throwable.getMessage() != null ? throwable.getMessage() : "Fallback Post Persona",
                throwable.getClass().getSimpleName(),
                LocalDate.now(),
                throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
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
   * GET PERSONA POR ID
   * ------------------------------------------
   */
  @GetMapping("/{id}")
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
                throwable.getMessage() != null ? throwable.getMessage() : "Fallback Get Persona By Id",
                throwable.getClass().getSimpleName(),
                LocalDate.now(),
                throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
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
   * GET PERSONAS
   * ------------------------------------------
   */
  @GetMapping("/all")
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
            throwable.getMessage() != null ? throwable.getMessage() : "Fallback Get All Personas",
            throwable.getClass().getSimpleName(),
            LocalDate.now(),
            throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
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
}
