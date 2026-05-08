package com.cybindev.project.service;

import java.util.List;

import com.cybindev.project.domain.ProyectoResponseDTO;

public interface PersonaProjectsService<O, I> {
  O crearPersonaProject(I personaProject);

  O obtenerPersonaProjectPorId(Long id);

  List<ProyectoResponseDTO> obtenerProjectsPorPersonaId(Long personaId);

}
