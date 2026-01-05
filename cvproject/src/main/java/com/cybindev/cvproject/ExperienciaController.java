package com.cybindev.cvproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExperienciaController {

  @GetMapping("/all-experiencia")
  public String getAllExperiencia() {
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
