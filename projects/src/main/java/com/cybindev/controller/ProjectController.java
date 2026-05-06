package com.cybindev.controller;

import com.cybindev.domain.PersonaProyectoRequestDTO;
import com.cybindev.domain.PersonaProyectoResponseDTO;
import com.cybindev.domain.ProyectoRequestDTO;
import com.cybindev.domain.ProyectoResponseDTO;
import com.cybindev.service.PersonaProjectsService;
import com.cybindev.service.ProyectoService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

  private final ProyectoService<ProyectoResponseDTO, ProyectoRequestDTO> service;
  private final PersonaProjectsService<PersonaProyectoResponseDTO, PersonaProyectoRequestDTO> personaProjectsService;

  public ProjectController(ProyectoService<ProyectoResponseDTO, ProyectoRequestDTO> s,
      PersonaProjectsService<PersonaProyectoResponseDTO, PersonaProyectoRequestDTO> personaProjectsService) {
    this.service = s;
    this.personaProjectsService = personaProjectsService;
  }

  @PostConstruct
  public void init() {
    System.out.println("Proyectos Controller initialized");
  }

  /*
   * ------------------------------------------
   * HEALTH CHECK
   * ------------------------------------------
   */
  @GetMapping
  public ResponseEntity<String> health() {
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
    return ResponseEntity.status(HttpStatus.OK)
        .body(this.service.listarProyectos());
  }

  public ResponseEntity<List<ProyectoResponseDTO>> fallBackGetAllProyectos(Throwable throwable) {
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
   * POST PROYECTOS
   * ------------------------------------------
   */
  @PostMapping
  @CircuitBreaker(name = "createProyctoService", fallbackMethod = "fallBackCreateProyecto")
  public ResponseEntity<ProyectoResponseDTO> postProyecto(@RequestBody ProyectoRequestDTO request) {
    final ProyectoResponseDTO response = this.service.crearProyecto(request);
    return ResponseEntity.status(HttpStatus.OK)
        .body(response);
  }

  public ResponseEntity<ProyectoResponseDTO> fallBackCreateProyecto(@RequestBody ProyectoRequestDTO request,
      Throwable throwable) {
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
    final ProyectoResponseDTO response = this.service.obtenerProyectoPorId(id);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
  }

  public ResponseEntity<ProyectoResponseDTO> fallBackGetProyectoPorId(@PathVariable Long id, Throwable throwable) {
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
    List<ProyectoResponseDTO> response = this.personaProjectsService.obtenerProjectsPorPersonaId(personaId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(response);
  }

  public ResponseEntity<List<ProyectoResponseDTO>> fallBackGetProyectosPorPersonaId(@PathVariable Long personaId,
      Throwable throwable) {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(List.of(new ProyectoResponseDTO(
            -1L,
            throwable.getMessage() != null ? throwable.getMessage() : "Fallback Get Proyectos Por Persona ID",
            throwable.getClass().getSimpleName(),
            throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
            "N/A")));
  }
}
