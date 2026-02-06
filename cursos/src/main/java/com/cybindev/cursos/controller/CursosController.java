package com.cybindev.cursos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cybindev.cursos.domain.Curso;
import com.cybindev.cursos.domain.CursoRequestDTO;
import com.cybindev.cursos.domain.CursoResponseDTO;
import com.cybindev.cursos.service.CursoService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;

@RestController
public class CursosController {

  private final CursoService<CursoResponseDTO, CursoRequestDTO> cursoService;

  public CursosController(CursoService<CursoResponseDTO, CursoRequestDTO> cursoService) {
    this.cursoService = cursoService;
  }

  @PostConstruct
  public void init() {
    System.out.println("Cursos Controller initialized");
  }

  /*
   * ------------------------------------------
   * HEALTH CHECK
   * ------------------------------------------
   */
  @GetMapping
  public ResponseEntity<String> health() {
    return ResponseEntity.status(HttpStatus.OK)
        .body("Cursos controller is healthy");
  }
  /*
   * ------------------------------------------
   * !HEALTH CHECK
   * ------------------------------------------
   */

  /*
   * ------------------------------------------
   * GET ALL CURSOS
   * ------------------------------------------
   */
  @GetMapping("/getCursos")
  @CircuitBreaker(name = "getAllCursosService", fallbackMethod = "fallbackGetAll")
  public ResponseEntity<List<CursoResponseDTO>> getCursos() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(cursoService.listarCursos());
  }

  public ResponseEntity<List<CursoResponseDTO>> fallbackGetAll(Throwable throwable) {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(List.of(new CursoResponseDTO(
            -1L,
            "Fallback GetAll Curso",
            "N/A",
            "N/A",
            "N/A",
            "No se han podido obtener los cursos",
            "N/A")));
  }
  /*
   * ------------------------------------------
   * !GET ALL CURSOS
   * ------------------------------------------
   */

  /*
   * ------------------------------------------
   * POST CURSO
   * ------------------------------------------
   */
  @PostMapping("/postCurso")
  @CircuitBreaker(name = "postCursoService", fallbackMethod = "fallbackPostCurso")
  public ResponseEntity<CursoResponseDTO> postCurso(@RequestBody CursoRequestDTO request) {
    CursoResponseDTO response = this.cursoService.crearCurso(request);
    return ResponseEntity.status(HttpStatus.OK)
        .body(response);
  }

  public ResponseEntity<CursoResponseDTO> fallbackPostCurso(@RequestBody CursoRequestDTO request, Throwable throwable) {
    CursoResponseDTO fallbackResponse = new CursoResponseDTO(
        -1L,
        "Fallback Post Curso",
        "N/A",
        "N/A",
        "N/A",
        "No se ha podido crear el curso",
        "N/A");

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(fallbackResponse);
  }
  /*
   * ------------------------------------------
   * !POST CURSO
   * ------------------------------------------
   */

  /*
   * ------------------------------------------
   * GET CURSO BY ID
   * ------------------------------------------
   */
  @GetMapping("/getCurso/{id}")
  @CircuitBreaker(name = "getCursoByIdService", fallbackMethod = "fallbackGetById")
  public ResponseEntity<CursoResponseDTO> getCursoById(@PathVariable Long id) {
    CursoResponseDTO response = this.cursoService.obtenerCursoPorId(id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(response);
  }

  public ResponseEntity<CursoResponseDTO> fallbackGetById(@PathVariable Long id, Throwable throwable) {
    CursoResponseDTO fallbackResponse = new CursoResponseDTO(
        -1L,
        "Fallback GetById Curso",
        "N/A",
        "N/A",
        "N/A",
        "No se ha podido obtener el curso con id: " + id,
        "N/A");

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(fallbackResponse);
  }
  /*
   * ------------------------------------------
   * !GET CURSO BY ID
   * ------------------------------------------
   */

}
