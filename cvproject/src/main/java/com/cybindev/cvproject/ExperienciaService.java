package com.cybindev.cvproject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExperienciaService {
  ExperienciaResponseDTO getExperienciaById(Long id);

  Experiencia addExperiencia(ExperienciaRequestDTO experienciaDTO);

  Page<ExperienciaResponseDTO> getExperienciaList(Pageable pageable);
}
