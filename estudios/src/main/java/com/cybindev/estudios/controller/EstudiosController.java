package com.cybindev.estudios.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybindev.estudios.domain.EstudioRequestDTO;
import com.cybindev.estudios.domain.EstudioResponseDTO;
import com.cybindev.estudios.service.EstudioService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/estudios")
public class EstudiosController {

  private final EstudioService<EstudioResponseDTO, EstudioRequestDTO> estudioService;

  public EstudiosController(EstudioService<EstudioResponseDTO, EstudioRequestDTO> estudioService) {
    this.estudioService = estudioService;
  }

  @PostConstruct
  public void init() {
    System.out.println("Estudios Controller initialized");
  }

  /*
   * ------------------------------------------
   * HEALTH CHECK
   * ------------------------------------------
   */
  @GetMapping
  public ResponseEntity<String> health() {
    return ResponseEntity.status(HttpStatus.OK)
        .body("Estudios controller is healthy");
  }

  /*
   * ------------------------------------------
   * GET ALL ESTUDIOS
   * ------------------------------------------
   */
  @GetMapping("/all")
  @CircuitBreaker(name = "getAllEstudiosService", fallbackMethod = "fallbackGetAll")
  public ResponseEntity<List<EstudioResponseDTO>> getEstudios() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(estudioService.listarEstudios());
  }

  public ResponseEntity<List<EstudioResponseDTO>> fallbackGetAll(Throwable throwable) {
    EstudioResponseDTO fallbackResponse = new EstudioResponseDTO(
        -1L,
        throwable.getMessage() != null ? throwable.getMessage() : "Fallback GetAll",
        throwable.getClass().getSimpleName(),
        throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
        "Fallback response due to service unavailability",
        "No Courses");

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(List.of(fallbackResponse));
  }

  /*
   * ------------------------------------------
   * POST ESTUDIO
   * ------------------------------------------
   */
  @PostMapping
  public ResponseEntity<EstudioResponseDTO> postEstudio(@RequestBody EstudioRequestDTO estudio) {
    EstudioResponseDTO creado = estudioService.crearEstudio(estudio);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(creado);
  }

  public ResponseEntity<EstudioResponseDTO> fallbackPostEstudio(@RequestBody EstudioRequestDTO estudio,
      Throwable throwable) {
    EstudioResponseDTO fallbackResponse = new EstudioResponseDTO(
        -1L,
        throwable.getMessage() != null ? throwable.getMessage() : "Fallback PostEstudio",
        throwable.getClass().getSimpleName(),
        throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
        "Fallback response due to service unavailability",
        "No Courses");

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(fallbackResponse);
  }

  /*
   * ------------------------------------------
   * GET ESTUDIO BY ID
   * ------------------------------------------
   */
  @GetMapping("/{id}")
  @CircuitBreaker(name = "getEstudioService", fallbackMethod = "fallbackGetById")
  public ResponseEntity<EstudioResponseDTO> getEstudio(@PathVariable Long id) {
    EstudioResponseDTO sampleEstudio = estudioService.obtenerEstudioPorId(id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(sampleEstudio);
  }

  public ResponseEntity<EstudioResponseDTO> fallbackGetById(@PathVariable Long id, Throwable throwable) {
    EstudioResponseDTO fallbackResponse = new EstudioResponseDTO(
        -1L,
        throwable.getMessage() != null ? throwable.getMessage() : "Fallback GetEstudioById",
        throwable.getClass().getSimpleName(),
        throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
        "Fallback response due to service unavailability",
        "No Courses");

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(fallbackResponse);
  }
}
