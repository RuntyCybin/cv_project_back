package com.cybindev.cvproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExperienciaService<O, I> {
  O getExperienciaById(Long id);

  O addExperiencia(I experienciaDTO);

  Page<O> getExperienciaList(Pageable pageable);

  void eliminarExperiencia(I experiencia);
}
