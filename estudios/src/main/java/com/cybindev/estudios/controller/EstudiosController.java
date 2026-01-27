package com.cybindev.estudios.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybindev.estudios.domain.EstudioRequestDTO;
import com.cybindev.estudios.domain.EstudioResponseDTO;
import com.cybindev.estudios.service.EstudioService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class EstudiosController {

  private final EstudioService<EstudioResponseDTO, EstudioRequestDTO> estudioService;

  public EstudiosController(EstudioService estudioService) {
    this.estudioService = estudioService;
  }

  @GetMapping
  public ResponseEntity<String> health() {
    return ResponseEntity.status(HttpStatus.OK)
        .body("Estudios controller is healthy");
  }

  /*
   * GET ALL ESTUDIOS
   */
  @GetMapping("/getEstudios")
  @CircuitBreaker(name = "estudiosService", fallbackMethod = "fallbackGetAll")
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
   * !GET ALL ESTUDIOS
   */

}
