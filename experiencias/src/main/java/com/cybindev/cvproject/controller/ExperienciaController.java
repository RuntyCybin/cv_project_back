package com.cybindev.cvproject.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cybindev.cvproject.domain.ExperienciaRequestDTO;
import com.cybindev.cvproject.domain.ExperienciaResponseDTO;
import com.cybindev.cvproject.service.ExperienciaService;

@RestController
public class ExperienciaController {

  private final ExperienciaService<ExperienciaResponseDTO, ExperienciaRequestDTO> experienciaService;

  public ExperienciaController(ExperienciaService<ExperienciaResponseDTO, ExperienciaRequestDTO> experienciaService) {
    this.experienciaService = experienciaService;
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
  @GetMapping("/all-experiencia")
  public Page<ExperienciaResponseDTO> getAllExperiencia(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
    return experienciaService.getExperienciaList(pageable);
  }

  /*
   * ------------------------------------------
   * GET EXPERIENCIA BY ID
   * ------------------------------------------
   */
  @GetMapping("/experiencia/{id}")
  public ResponseEntity<ExperienciaResponseDTO> getExperienciaById(@PathVariable final Long id) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(this.experienciaService.getExperienciaById(id));
  }

  /*
   * ------------------------------------------
   * POST EXPERIENCIA
   * ------------------------------------------
   */
  @PostMapping("/add-experiencia")
  public ResponseEntity<String> addExperiencia(@RequestBody ExperienciaRequestDTO experienciaDTO) {
    experienciaService.addExperiencia(experienciaDTO);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body("Experiencia added successfully");
  }
}
