package com.cybindev.skills.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cybindev.skills.domain.PersonaSkillRequestDTO;
import com.cybindev.skills.domain.PersonaSkillResponseDTO;
import com.cybindev.skills.domain.SkillRequestDTO;
import com.cybindev.skills.domain.SkillResponseDTO;
import com.cybindev.skills.service.PersonaSkillService;
import com.cybindev.skills.service.SkillService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/skills")
public class SkillController {
  private final Logger logger = LoggerFactory.getLogger(SkillController.class);
  private final SkillService<SkillResponseDTO, SkillRequestDTO> service;
  private final PersonaSkillService<PersonaSkillResponseDTO, PersonaSkillRequestDTO> personaSkillService;

  public SkillController(SkillService<SkillResponseDTO, SkillRequestDTO> service,
      PersonaSkillService<PersonaSkillResponseDTO, PersonaSkillRequestDTO> personaSkillService) {
    this.service = service;
    this.personaSkillService = personaSkillService;
  }

  @PostConstruct
  public void init() {
    logger.info("Skills Controller initialized");
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
        .body("Skills controller is healthy");
  }

  /*
   * ------------------------------------------
   * POST SKILL
   * ------------------------------------------
   */
  @PostMapping
  @CircuitBreaker(name = "createSkillService", fallbackMethod = "fallBackCreateSkill")
  public ResponseEntity<SkillResponseDTO> postSkill(@RequestBody SkillRequestDTO request) {
    logger.info("Received request to create skill: {}", request);
    SkillResponseDTO response = service.crearSkill(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  public ResponseEntity<SkillResponseDTO> fallBackCreateSkill(@RequestBody SkillRequestDTO request,
      Throwable throwable) {
    logger.error("Error occurred while creating skill", throwable);
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(new SkillResponseDTO(
            -1L,
            throwable.getMessage() != null ? throwable.getMessage() : "Fallback Create Skill",
            Boolean.FALSE));
  }

  /*
   * ------------------------------------------
   * GET SKILL POR ID
   * ------------------------------------------
   */
  @GetMapping("/{id}")
  @CircuitBreaker(name = "getSkillById", fallbackMethod = "fallBackGetSkillById")
  public ResponseEntity<SkillResponseDTO> getSkillById(@PathVariable Long id) {
    logger.info("Received request to fetch skill with id: {}", id);
    SkillResponseDTO response = service.obtenerSkillPorId(id);
    return ResponseEntity.ok(response);
  }

  public ResponseEntity<SkillResponseDTO> fallBackGetSkillById(@PathVariable Long id, Throwable throwable) {
    logger.error("Error occurred while fetching skill by id: {}", id, throwable);
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(new SkillResponseDTO(
            -1L,
            throwable.getMessage() != null ? throwable.getMessage() : "Fallback Get Skill by Id",
            Boolean.FALSE));
  }

  /*
   * ------------------------------------------
   * GET ALL SKILLS
   * ------------------------------------------
   */
  @GetMapping("/all")
  @CircuitBreaker(name = "getAllSkills", fallbackMethod = "fallBackGetAllSkills")
  public ResponseEntity<List<SkillResponseDTO>> getSkills() {
    List<SkillResponseDTO> skills = service.listarSkills();
    logger.info("Received request to fetch all skills, returning " + skills.size() + " skills");
    return ResponseEntity.ok(skills);
  }

  public ResponseEntity<List<SkillResponseDTO>> fallBackGetAllSkills(Throwable throwable) {
    logger.error("Error occurred while fetching all skills", throwable);
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(Arrays.asList(new SkillResponseDTO(
            -1L,
            throwable.getMessage() != null ? throwable.getMessage() : "Fallback Get All Skills",
            Boolean.FALSE)));
  }

  /*
   * ------------------------------------------
   * GET SKILLS POR PERSONA ID
   * ------------------------------------------
   */
  @GetMapping("/persona/{personaId}")
  @CircuitBreaker(name = "getSkillsByPersonaId", fallbackMethod = "fallBackGetSkillsByPersonaId")
  public ResponseEntity<List<SkillResponseDTO>> getSkillsByPersonaId(@PathVariable Long personaId) {
    logger.info("Received request to fetch skills for persona with id: {}", personaId);
    List<SkillResponseDTO> response = personaSkillService.obtenerSkillsPorPersonaId(personaId);
    return ResponseEntity.ok(response);
  }

  public ResponseEntity<List<SkillResponseDTO>> fallBackGetSkillsByPersonaId(@PathVariable Long personaId,
      Throwable throwable) {
    logger.error("Error occurred while fetching skills by persona id: {}", personaId, throwable);
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(Arrays.asList(new SkillResponseDTO(
            -1L,
            throwable.getMessage() != null ? throwable.getMessage() : "Fallback Get Skills by Persona Id",
            Boolean.FALSE)));
  }

  /*
   * ------------------------------------------
   * POST PERSONA-SKILL
   * ------------------------------------------
   */
  @PostMapping("/persona-skill")
  @CircuitBreaker(name = "createPersonaSkill", fallbackMethod = "fallBackCreatePersonaSkill")
  public ResponseEntity<PersonaSkillResponseDTO> postPersonaSkill(@RequestBody PersonaSkillRequestDTO request) {
    logger.info("Received request to create persona-skill association: {}", request);
    PersonaSkillResponseDTO response = personaSkillService.crearPersonaSkill(request);
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }

  public ResponseEntity<PersonaSkillResponseDTO> fallBackCreatePersonaSkill(
      @RequestBody PersonaSkillRequestDTO request,
      Throwable throwable) {
    logger.error("Error occurred while creating persona-skill association", throwable);
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(new PersonaSkillResponseDTO(-1L, -1L, -1L));
  }
}
