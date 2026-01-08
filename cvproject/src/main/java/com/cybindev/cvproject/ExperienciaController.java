package com.cybindev.cvproject;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cv")
public class ExperienciaController {

  private final ExperienciaService experienciaService;

  ExperienciaController(ExperienciaService experienciaService) {
    this.experienciaService = experienciaService;
  }

  @GetMapping
  public String health() {
    return "OK";
  }

  @GetMapping("/all-experiencia")
  public String getAllExperiencia(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
    return "List of all experiencia";
  }

  @GetMapping("/experiencia/{id}")
  public String getExperienciaById(@PathVariable final Long id) {

    ExperienciaResponseDTO experiencia = experienciaService.getExperienciaById(id);

    return "Experiencia en el puesto " + experiencia.puesto() + " details";
  }

  @PostMapping("/add-experiencia")
  public String addExperiencia() {
    return "Experiencia added successfully";
  }
}
