package com.cybindev.cvproject;

public class ExperienciaServiceImpl implements ExperienciaService {

  private final ExperienciaRepo experienciaRepo;

  public ExperienciaServiceImpl(ExperienciaRepo experienciaRepo) {
    this.experienciaRepo = experienciaRepo;
  }

}
