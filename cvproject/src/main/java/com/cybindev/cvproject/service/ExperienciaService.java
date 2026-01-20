package com.cybindev.cvproject.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cybindev.cvproject.domain.Experiencia;
import com.cybindev.cvproject.domain.ExperienciaRequestDTO;
import com.cybindev.cvproject.domain.ExperienciaResponseDTO;

public interface ExperienciaService {
  ExperienciaResponseDTO getExperienciaById(Long id);

  Experiencia addExperiencia(ExperienciaRequestDTO experienciaDTO);

  Page<ExperienciaResponseDTO> getExperienciaList(Pageable pageable);
}
