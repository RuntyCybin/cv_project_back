package com.cybindev.cvproject;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ExperienciaController {

  @GetMapping("/all-experiencia")
  public String getAllExperiencia(Pageable pageable) {
    return "List of all experiencia";
  }

  @GetMapping("/experiencia/{id}")
  public String getExperienciaById(@PathVariable final Long id) {
    return "Experiencia " + id + " details by ID ";
  }

  @PostMapping("/add-experiencia")
  public String addExperiencia() {
    return "Experiencia added successfully";
  }
}
