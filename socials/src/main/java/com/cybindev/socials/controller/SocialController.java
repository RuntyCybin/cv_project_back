package com.cybindev.socials.controller;

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

import com.cybindev.socials.domain.PersonaSocialRequestDTO;
import com.cybindev.socials.domain.PersonaSocialResponseDTO;
import com.cybindev.socials.domain.SocialRequestDTO;
import com.cybindev.socials.domain.SocialResponseDTO;
import com.cybindev.socials.service.SocialsService;
import com.cybindev.socials.service.impl.PersonaSocialServiceImpl;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/socials")
public class SocialController {

  private final Logger logger = LoggerFactory.getLogger(SocialController.class);
  private final SocialsService<SocialResponseDTO, SocialRequestDTO> socialService;
  private final PersonaSocialServiceImpl personaSocialService;

  public SocialController(SocialsService<SocialResponseDTO, SocialRequestDTO> socialService,
      PersonaSocialServiceImpl personaSocialService) {
    logger.info("SocialController initialized");
    this.socialService = socialService;
    this.personaSocialService = personaSocialService;
  }

  @PostConstruct
  public void init() {
    logger.info("Social Controller initialized");
  }

  /*
   * ------------------------------------------
   * HEALTH CHECK
   * ------------------------------------------
   */
  @GetMapping
  public ResponseEntity<String> health() {
    return ResponseEntity.status(HttpStatus.OK)
        .body("Socials controller is healthy");
  }

  /*
   * ------------------------------------------
   * POST SOCIAL
   * ------------------------------------------
   */
  @PostMapping
  @CircuitBreaker(name = "postSocialService", fallbackMethod = "fallbackPostSocial")
  public ResponseEntity<SocialResponseDTO> postSocial(@RequestBody SocialRequestDTO social) {
    SocialResponseDTO creado = socialService.crearRedSocial(social);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(creado);
  }

  public ResponseEntity<SocialResponseDTO> fallbackPostSocial(@RequestBody SocialRequestDTO social,
      Throwable throwable) {
    SocialResponseDTO fallbackResponse = new SocialResponseDTO(
        -1L,
        throwable.getMessage() != null ? throwable.getMessage() : "Fallback PostSocial",
        throwable.getClass().getSimpleName(),
        throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
        "Fallback response due to service unavailability");

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(fallbackResponse);
  }

  /*
   * ------------------------------------------
   * GET SOCIAL BY ID
   * ------------------------------------------
   */
  @GetMapping("/{id}")
  @CircuitBreaker(name = "getSocialByIdService", fallbackMethod = "fallbackGetSocialById")
  public ResponseEntity<SocialResponseDTO> getSocialById(@PathVariable Long id) {
    SocialResponseDTO social = socialService.obtenerRedSocialPorId(id);
    return ResponseEntity.status(HttpStatus.OK)
        .body(social);
  }

  public ResponseEntity<SocialResponseDTO> fallbackGetSocialById(@PathVariable Long id,
      Throwable throwable) {
    SocialResponseDTO fallbackResponse = new SocialResponseDTO(
        -1L,
        throwable.getMessage() != null ? throwable.getMessage() : "Fallback GetSocialById",
        throwable.getClass().getSimpleName(),
        throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
        "Fallback response due to service unavailability");
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(fallbackResponse);
  }

  /*
   * ------------------------------------------
   * GET ALL SOCIALS
   * ------------------------------------------
   */
  @GetMapping("/all")
  @CircuitBreaker(name = "getAllSocialsService", fallbackMethod = "fallbackGetAllSocials")
  public ResponseEntity<List<SocialResponseDTO>> getAllSocials() {
    return ResponseEntity.status(HttpStatus.OK)
        .body(socialService.listarRedesSociales());
  }

  public ResponseEntity<List<SocialResponseDTO>> fallbackGetAllSocials(Throwable throwable) {
    SocialResponseDTO fallbackResponse = new SocialResponseDTO(
        -1L,
        throwable.getMessage() != null ? throwable.getMessage() : "Fallback GetAllSocials",
        throwable.getClass().getSimpleName(),
        throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
        "Fallback response due to service unavailability");

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(Arrays.asList(fallbackResponse));
  }

  /*
   * ------------------------------------------
   * GET SOCIALS BY PERSONA ID
   * ------------------------------------------
   */
  @GetMapping("/persona/{personaId}")
  @CircuitBreaker(name = "getSocialsByPersonaIdService", fallbackMethod = "fallbackGetSocialsByPersonaId")
  public ResponseEntity<List<SocialResponseDTO>> getSocialsByPersonaId(@PathVariable Long personaId) {
    return ResponseEntity.status(HttpStatus.OK)
        .body(this.personaSocialService.obtenerRedesSocialesPorPersonaId(personaId));
  }

  public ResponseEntity<List<SocialResponseDTO>> fallbackGetSocialsByPersonaId(@PathVariable Long personaId,
      Throwable throwable) {
    SocialResponseDTO fallbackResponse = new SocialResponseDTO(
        -1L,
        throwable.getMessage() != null ? throwable.getMessage() : "Fallback GetSocialsByPersonaId",
        throwable.getClass().getSimpleName(),
        throwable.getCause() != null ? throwable.getCause().toString() : "N/A",
        "Fallback response due to service unavailability");

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(Arrays.asList(fallbackResponse));
  }

  /*
   * ------------------------------------------
   * POST SOCIALS BY PERSONA ID
   * ------------------------------------------
   */
  @PostMapping("/persona")
  @CircuitBreaker(name = "postSocialsByPersonaIdService", fallbackMethod = "fallbackPostSocialsByPersonaId")
  public ResponseEntity<PersonaSocialResponseDTO> postSocialsByPersonaId(
      @RequestBody PersonaSocialRequestDTO socialRequest) {
    PersonaSocialResponseDTO creado = this.personaSocialService.crearPersonaRedSocial(socialRequest);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(creado);
  }

  public ResponseEntity<PersonaSocialResponseDTO> fallbackPostSocialsByPersonaId(
      @RequestBody PersonaSocialRequestDTO socialRequest, Throwable throwable) {
    PersonaSocialResponseDTO fallbackResponse = new PersonaSocialResponseDTO(
        -1L,
        socialRequest.personaId(),
        socialRequest.socialId());

    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
        .body(fallbackResponse);
  }
}
