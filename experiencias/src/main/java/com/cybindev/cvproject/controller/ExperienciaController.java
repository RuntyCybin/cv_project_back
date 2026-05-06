package com.cybindev.cvproject.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybindev.cvproject.domain.ExperienciaRequestDTO;
import com.cybindev.cvproject.domain.ExperienciaResponseDTO;
import com.cybindev.cvproject.domain.PersonaExperienciaRequestDTO;
import com.cybindev.cvproject.domain.PersonaExperienciaResponseDTO;
import com.cybindev.cvproject.service.ExperienciaService;
import com.cybindev.cvproject.service.PersonaExperienciaService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
@RequestMapping("/experiencias")
public class ExperienciaController {

  private final Logger logger = LoggerFactory.getLogger(ExperienciaController.class);
  private final ExperienciaService<ExperienciaResponseDTO, ExperienciaRequestDTO> experienciaService;
  private final PersonaExperienciaService<PersonaExperienciaResponseDTO, PersonaExperienciaRequestDTO> personaExperienciaService;

  public ExperienciaController(ExperienciaService<ExperienciaResponseDTO, ExperienciaRequestDTO> experienciaService,
      PersonaExperienciaService<PersonaExperienciaResponseDTO, PersonaExperienciaRequestDTO> personaExperienciaService) {
    this.experienciaService = experienciaService;
    this.personaExperienciaService = personaExperienciaService;
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
        .body("Endopoint CV is healthy");
  }

  /*
   * ------------------------------------------
   * GET ALL EXPERIENCIAS
   * ------------------------------------------
   */
  @GetMapping("/all")
  public Page<ExperienciaResponseDTO> getAllExperiencia(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
    return experienciaService.getExperienciaList(pageable);
  }

  /*
   * ------------------------------------------
   * GET EXPERIENCIA BY ID
   * ------------------------------------------
   */
  @GetMapping("/{id}")
  @CircuitBreaker(name = "getExperiencia", fallbackMethod = "getExperienciaByIdFallback")
  public ResponseEntity<ExperienciaResponseDTO> getExperienciaById(@PathVariable final Long id) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(this.experienciaService.getExperienciaById(id));
  }

  public ResponseEntity<ExperienciaResponseDTO> getExperienciaByIdFallback(@PathVariable final Long id,
      Throwable throwable) {
    logger.error("Error al obtener experiencia con id: " + id);
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ExperienciaResponseDTO(
            id,
            throwable.getMessage() != null ? throwable.getMessage() : "Fallback Get Experiencia By Id",
            throwable.getClass().getSimpleName(),
            throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
            "01-1970",
            "01-1970"));
  }

  /*
   * ------------------------------------------
   * POST EXPERIENCIA
   * ------------------------------------------
   */
  @PostMapping
  @CircuitBreaker(name = "addExperiencia", fallbackMethod = "addExperienciaFallback")
  public ResponseEntity<ExperienciaResponseDTO> addExperiencia(@RequestBody ExperienciaRequestDTO experienciaDTO) {
    ExperienciaResponseDTO response = experienciaService.addExperiencia(experienciaDTO);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(response);
  }

  public ResponseEntity<ExperienciaResponseDTO> addExperienciaFallback(
      @RequestBody ExperienciaRequestDTO experienciaDTO,
      Throwable throwable) {
    logger.error("Error al agregar experiencia: " + experienciaDTO);
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ExperienciaResponseDTO(
            -1L,
            throwable.getMessage() != null ? throwable.getMessage() : "Fallback Add Experiencia",
            throwable.getClass().getSimpleName(),
            throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
            "01-1970",
            "01-1970"));
  }

  /*
   * ------------------------------------------
   * GET EXPERIENCIAS POR PERSONA ID
   * ------------------------------------------
   */
  @GetMapping("/persona/{personaId}")
  @CircuitBreaker(name = "getExperienciasByPersonaId", fallbackMethod = "getExperienciasByPersonaIdFallback")
  public ResponseEntity<List<ExperienciaResponseDTO>> getExperienciasByPersonaId(@PathVariable Long personaId,
      @PageableDefault(size = 10, sort = "id") Pageable pageable) {
    List<ExperienciaResponseDTO> response = personaExperienciaService.obtenerExperienciasPorPersonaId(personaId);
    return ResponseEntity.status(HttpStatus.OK)
        .body(response);
  }

  public ResponseEntity<List<ExperienciaResponseDTO>> getExperienciasByPersonaIdFallback(@PathVariable Long personaId,
      @PageableDefault(size = 10, sort = "id") Pageable pageable, Throwable throwable) {
    logger.error("Error al obtener experiencias para persona con id: " + personaId);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(List.of(new ExperienciaResponseDTO(
            -1L,
            throwable.getMessage() != null ? throwable.getMessage() : "Fallback Get Experiencias By Persona Id",
            throwable.getClass().getSimpleName(),
            throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
            "01-1970",
            "01-1970")));
  }
}
