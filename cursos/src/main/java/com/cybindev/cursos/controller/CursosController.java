package com.cybindev.cursos.controller;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cybindev.cursos.domain.CursoRequestDTO;
import com.cybindev.cursos.domain.CursoResponseDTO;
import com.cybindev.cursos.domain.PersonaCursoRequestDTO;
import com.cybindev.cursos.domain.PersonaCursoResponseDTO;
import com.cybindev.cursos.service.CursoService;
import com.cybindev.cursos.service.PersonaCursoService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/cursos")
public class CursosController {
  private final Logger logger = LoggerFactory.getLogger(CursosController.class);
  private final CursoService<CursoResponseDTO, CursoRequestDTO> cursoService;
  private final PersonaCursoService<PersonaCursoResponseDTO, PersonaCursoRequestDTO> personaCursoService;

  public CursosController(
      CursoService<CursoResponseDTO, CursoRequestDTO> cursoService,
      PersonaCursoService<PersonaCursoResponseDTO, PersonaCursoRequestDTO> personaCursoService) {
    this.cursoService = cursoService;
    this.personaCursoService = personaCursoService;
  }

  @PostConstruct
  public void init() {
    this.logger.info("Cursos Controller initialized");
  }

  /*
   * ------------------------------------------
   * HEALTH CHECK
   * ------------------------------------------
   */
  @GetMapping
  public ResponseEntity<String> health() {
    this.logger.info("Health check requested");
    return ResponseEntity.status(HttpStatus.OK)
        .body("Cursos controller is healthy");
  }

  /*
   * ------------------------------------------
   * GET ALL CURSOS
   * ---------------------------a---------------
   */
  @GetMapping("/all")
  @CircuitBreaker(name = "getAllCursosService", fallbackMethod = "fallbackGetAll")
  public ResponseEntity<List<CursoResponseDTO>> getCursos() {
    this.logger.info("Received request to fetch all cursos");
    return ResponseEntity.status(HttpStatus.OK)
        .body(this.cursoService.listarCursos());
  }

  public ResponseEntity<List<CursoResponseDTO>> fallbackGetAll(Throwable throwable) {
    this.logger.error("Error occurred while fetching all cursos", throwable);
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(List.of(new CursoResponseDTO(
            -1L,
            throwable.getMessage() != null ? throwable.getMessage() : "Fallback GetAll Curso",
            throwable.getClass().getSimpleName(),
            throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
            "N/A",
            "No se han podido obtener los cursos",
            "N/A")));
  }

  /*
   * ------------------------------------------
   * POST CURSO
   * ------------------------------------------
   */
  @PostMapping
  @CircuitBreaker(name = "postCursoService", fallbackMethod = "fallbackPostCurso")
  public ResponseEntity<CursoResponseDTO> postCurso(@RequestBody CursoRequestDTO request) {
    this.logger.info("Received request to create curso: {}", Objects.requireNonNull(request));
    final CursoResponseDTO response = this.cursoService.crearCurso(request);
    return ResponseEntity.status(HttpStatus.OK)
        .body(response);
  }

  public ResponseEntity<CursoResponseDTO> fallbackPostCurso(@RequestBody CursoRequestDTO request, Throwable throwable) {
    this.logger.error("Error occurred while creating curso", throwable);
    final CursoResponseDTO fallbackResponse = new CursoResponseDTO(
        -1L,
        throwable.getMessage() != null ? throwable.getMessage() : "Fallback Post Curso",
        throwable.getClass().getSimpleName(),
        throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
        "N/A",
        "No se ha podido crear el curso",
        "N/A");

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(fallbackResponse);
  }

  /*
   * ------------------------------------------
   * GET CURSO BY ID
   * ------------------------------------------
   */
  @GetMapping("/{id}")
  @CircuitBreaker(name = "getCursoByIdService", fallbackMethod = "fallbackGetById")
  public ResponseEntity<CursoResponseDTO> getCursoById(@PathVariable Long id) {
    if (id <= 0) {
      throw new IllegalArgumentException("El ID debe ser un número positivo");
    }
    this.logger.info("Received request to fetch curso by ID: {}", id);
    final CursoResponseDTO response = this.cursoService.obtenerCursoPorId(id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(response);
  }

  public ResponseEntity<CursoResponseDTO> fallbackGetById(@PathVariable Long id, Throwable throwable) {
    this.logger.error("Error occurred while fetching curso by ID: {}", id, throwable);
    final CursoResponseDTO fallbackResponse = new CursoResponseDTO(
        -1L,
        throwable.getMessage() != null ? throwable.getMessage() : "Fallback GetCursoById Curso",
        throwable.getClass().getSimpleName(),
        throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
        "N/A",
        "No se ha podido obtener el curso con id: " + id,
        "N/A");

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(fallbackResponse);
  }

  /*
   * ------------------------------------------
   * GET CURSOS BY ID PERSONA
   * ------------------------------------------
   */
  @GetMapping("/persona/{idPersona}")
  @CircuitBreaker(name = "getCursosByPersonaService", fallbackMethod = "fallbackGetCursosByPersona")
  public ResponseEntity<List<CursoResponseDTO>> getCursosByPersona(@PathVariable Long idPersona) {
    if (idPersona <= 0) {
      throw new IllegalArgumentException("El ID de persona debe ser un número positivo");
    }
    this.logger.info("Received request to fetch cursos by persona ID: {}", idPersona);
    final List<CursoResponseDTO> response = this.personaCursoService.obtenerCursosPorPersonaId(idPersona);
    return ResponseEntity.status(HttpStatus.OK)
        .body(response);
  }

  public ResponseEntity<List<CursoResponseDTO>> fallbackGetCursosByPersona(@PathVariable Long idPersona,
      Throwable throwable) {
    this.logger.error("Error occurred while fetching cursos by persona ID: {}", idPersona, throwable);
    final CursoResponseDTO fallbackResponse = new CursoResponseDTO(
        -1L,
        throwable.getMessage() != null ? throwable.getMessage() : "Fallback GetCursosByPersona Curso",
        throwable.getClass().getSimpleName(),
        throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
        "N/A",
        "No se han podido obtener los cursos para la persona con id: " + idPersona,
        "N/A");

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(List.of(fallbackResponse));
  }

  /*
   * ------------------------------------------
   * POST PERSONA-CURSOS
   * ------------------------------------------
   */
  @PostMapping("/persona-curso")
  @CircuitBreaker(name = "postPersonaCursoService", fallbackMethod = "fallbackPostPersonaCurso")
  public ResponseEntity<PersonaCursoResponseDTO> postPersonaCurso(@RequestBody PersonaCursoRequestDTO request) {
    this.logger.info("Received request to create persona-curso: {}", Objects.requireNonNull(request));
    final PersonaCursoResponseDTO response = this.personaCursoService.crearPersonaCurso(request);
    return ResponseEntity.status(HttpStatus.OK)
        .body(response);
  }

  public ResponseEntity<PersonaCursoResponseDTO> fallbackPostPersonaCurso(@RequestBody PersonaCursoRequestDTO request,
      Throwable throwable) {
    this.logger.error("Error occurred while creating persona-curso", throwable);
    final PersonaCursoResponseDTO fallbackResponse = new PersonaCursoResponseDTO(-1L, -1L, -1L);

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(fallbackResponse);
  }
}
