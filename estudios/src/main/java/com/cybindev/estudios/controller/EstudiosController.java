package com.cybindev.estudios.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cybindev.estudios.domain.EstudioRequestDTO;
import com.cybindev.estudios.domain.EstudioResponseDTO;
import com.cybindev.estudios.service.EstudioService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;

@RestController
public class EstudiosController {

  private final EstudioService<EstudioResponseDTO, EstudioRequestDTO> estudioService;

  public EstudiosController(EstudioService estudioService) {
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
   * !HEALTH CHECK
   * ------------------------------------------
   */

  /*
   * ------------------------------------------
   * GET ALL ESTUDIOS
   * ------------------------------------------
   */
  @GetMapping("/getEstudios")
  @CircuitBreaker(name = "getAllEstudiosService", fallbackMethod = "fallbackGetAll")
  public ResponseEntity<List<EstudioResponseDTO>> getEstudios() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(estudioService.listarEstudios());
  }

  public ResponseEntity<List<EstudioResponseDTO>> fallbackGetAll(Throwable throwable) {
    EstudioResponseDTO fallbackResponse = new EstudioResponseDTO(
        -1L,
        "No Title",
        "No Institution",
        "No Period",
        "Fallback response due to service unavailability",
        "No Courses");

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(List.of(fallbackResponse));
  }
  /*
   * ------------------------------------------
   * !GET ALL ESTUDIOS
   * ------------------------------------------
   */

  /*
   * ------------------------------------------
   * POST ESTUDIO
   * ------------------------------------------
   */
  @PostMapping("/postEstudio")
  public ResponseEntity<EstudioResponseDTO> postEstudio(@RequestBody EstudioRequestDTO estudio) {
    EstudioResponseDTO creado = estudioService.crearEstudio(estudio);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(creado);
  }

  public ResponseEntity<EstudioResponseDTO> fallbackPostEstudio(@RequestBody EstudioRequestDTO estudio,
      Throwable throwable) {
    EstudioResponseDTO fallbackResponse = new EstudioResponseDTO(
        -1L,
        "No Title",
        "No Institution",
        "No Period",
        "Fallback response due to service unavailability",
        "No Courses");

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(fallbackResponse);
  }
  /*
   * ------------------------------------------
   * !POST ESTUDIO
   * ------------------------------------------
   */

  /*
   * ------------------------------------------
   * GET ESTUDIO
   * ------------------------------------------
   */
  @GetMapping("/getEstudio/{id}")
  @CircuitBreaker(name = "getEstudioService", fallbackMethod = "fallbackGetById")
  public ResponseEntity<EstudioResponseDTO> getEstudio(@PathVariable Long id) {
    EstudioResponseDTO sampleEstudio = estudioService.obtenerEstudioPorId(id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(sampleEstudio);
  }

  public ResponseEntity<EstudioResponseDTO> fallbackGetById(@PathVariable Long id, Throwable throwable) {
    EstudioResponseDTO fallbackResponse = new EstudioResponseDTO(
        -1L,
        "No Title",
        "No Institution",
        "No Period",
        "Fallback response due to service unavailability",
        "No Courses");

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(fallbackResponse);
  }
  /*
   * ------------------------------------------
   * !GET ESTUDIO
   * ------------------------------------------
   */

}
