package com.cybindev.project.controller;

import com.cybindev.project.domain.PersonaProyectoRequestDTO;
import com.cybindev.project.domain.PersonaProyectoResponseDTO;
import com.cybindev.project.domain.ProyectoRequestDTO;
import com.cybindev.project.domain.ProyectoResponseDTO;
import com.cybindev.project.service.PersonaProjectsService;
import com.cybindev.project.service.ProyectoService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/projects")
public class ProjectController {

  private final Logger logger = LoggerFactory.getLogger(ProjectController.class);
  private final ProyectoService<ProyectoResponseDTO, ProyectoRequestDTO> service;
  private final PersonaProjectsService<PersonaProyectoResponseDTO, PersonaProyectoRequestDTO> personaProjectsService;

  public ProjectController(ProyectoService<ProyectoResponseDTO, ProyectoRequestDTO> s,
      PersonaProjectsService<PersonaProyectoResponseDTO, PersonaProyectoRequestDTO> personaProjectsService) {
    this.service = s;
    this.personaProjectsService = personaProjectsService;
  }

  @PostConstruct
  public void init() {
    logger.info("Proyectos Controller initialized");
  }

  /*
   * ------------------------------------------
   * HEALTH CHECK
   * ------------------------------------------
   */
  @GetMapping
  public ResponseEntity<String> health() {
    logger.info("Health check requested");
    return ResponseEntity.status(HttpStatus.OK)
        .body("Proyectos controller is healthy");
  }

  /*
   * ------------------------------------------
   * GET ALL PROYECTOS
   * ------------------------------------------
   */
  @GetMapping("/all")
  @CircuitBreaker(name = "getAllProyectosService", fallbackMethod = "fallBackGetAllProyectos")
  public ResponseEntity<List<ProyectoResponseDTO>> getProyectos() {
    logger.info("Received request to fetch all proyectos");
    return ResponseEntity.status(HttpStatus.OK)
        .body(this.service.listarProyectos());
  }

  public ResponseEntity<List<ProyectoResponseDTO>> fallBackGetAllProyectos(Throwable throwable) {
    logger.error("Error occurred while fetching all proyectos", throwable);
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(List.of(new ProyectoResponseDTO(
            -1L,
            throwable.getMessage() != null ? throwable.getMessage() : "Fallback GetAll Proyectos",
            throwable.getClass().getSimpleName(),
            throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
            "N/A")));
  }

  /*
   * ------------------------------------------
   * POST PROYECTO
   * ------------------------------------------
   */
  @PostMapping
  @CircuitBreaker(name = "createProyctoService", fallbackMethod = "fallBackCreateProyecto")
  public ResponseEntity<ProyectoResponseDTO> postProyecto(@RequestBody ProyectoRequestDTO request) {
    logger.info("Received request to create proyecto: {}", Objects.requireNonNull(request));
    final ProyectoResponseDTO response = this.service.crearProyecto(request);
    return ResponseEntity.status(HttpStatus.OK)
        .body(response);
  }

  public ResponseEntity<ProyectoResponseDTO> fallBackCreateProyecto(@RequestBody ProyectoRequestDTO request,
      Throwable throwable) {
    logger.error("Error occurred while creating proyecto", throwable);
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(new ProyectoResponseDTO(
            -1L,
            throwable.getMessage() != null ? throwable.getMessage() : "Fallback Create Proyecto",
            throwable.getClass().getSimpleName(),
            throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
            "N/A"));
  }

  /*
   * ------------------------------------------
   * GET PROYECTO POR ID
   * ------------------------------------------
   */
  @GetMapping("/{id}")
  @CircuitBreaker(name = "getProyectoPorId", fallbackMethod = "fallBackGetProyectoPorId")
  public ResponseEntity<ProyectoResponseDTO> getCursoPorId(@PathVariable Long id) {
    if (id <= 0) {
      throw new IllegalArgumentException("El ID debe ser un número positivo");
    }
    logger.info("Received request to fetch proyecto by ID: {}", id);
    final ProyectoResponseDTO response = this.service.obtenerProyectoPorId(id);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }

  public ResponseEntity<ProyectoResponseDTO> fallBackGetProyectoPorId(@PathVariable Long id, Throwable throwable) {
    logger.error("Error occurred while fetching proyecto by ID: {}", id, throwable);
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(new ProyectoResponseDTO(
            -1L,
            throwable.getMessage() != null ? throwable.getMessage() : "Fallback Get Proyecto",
            throwable.getClass().getSimpleName(),
            throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
            "N/A"));
  }

  /*
   * ------------------------------------------
   * GET PROYECTOS POR PERSONA ID
   * ------------------------------------------
   */
  @GetMapping("/persona/{personaId}")
  @CircuitBreaker(name = "getProyectosPorPersonaId", fallbackMethod = "fallBackGetProyectosPorPersonaId")
  public ResponseEntity<List<ProyectoResponseDTO>> getProyectosPorPersonaId(@PathVariable Long personaId) {
    if (personaId <= 0) {
      throw new IllegalArgumentException("El ID de persona debe ser un número positivo");
    }
    logger.info("Received request to fetch proyectos for persona ID: {}", personaId);
    List<ProyectoResponseDTO> response = this.personaProjectsService.obtenerProjectsPorPersonaId(personaId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(response);
  }

  public ResponseEntity<List<ProyectoResponseDTO>> fallBackGetProyectosPorPersonaId(@PathVariable Long personaId,
      Throwable throwable) {
    logger.error("Error occurred while fetching proyectos by persona ID: {}", personaId, throwable);
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(List.of(new ProyectoResponseDTO(
            -1L,
            throwable.getMessage() != null ? throwable.getMessage() : "Fallback Get Proyectos Por Persona ID",
            throwable.getClass().getSimpleName(),
            throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
            "N/A")));
  }

  /*
   * ------------------------------------------
   * POST PERSONA-PROYECTO
   * ------------------------------------------
   */
  @PostMapping("/persona-project")
  @CircuitBreaker(name = "createPersonaProjectService", fallbackMethod = "fallBackCreatePersonaProject")
  public ResponseEntity<PersonaProyectoResponseDTO> postPersonaProject(
      @RequestBody PersonaProyectoRequestDTO request) {
    logger.info("Received request to create persona-project association: {}", Objects.requireNonNull(request));
    PersonaProyectoResponseDTO response = this.personaProjectsService.crearPersonaProject(request);
    return ResponseEntity.status(HttpStatus.OK)
        .body(response);
  }

  public ResponseEntity<PersonaProyectoResponseDTO> fallBackCreatePersonaProject(
      @RequestBody PersonaProyectoRequestDTO request, Throwable throwable) {
    logger.error("Error occurred while creating persona-project", throwable);
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(new PersonaProyectoResponseDTO(
            -1L, -1L, -1L));
  }
}
