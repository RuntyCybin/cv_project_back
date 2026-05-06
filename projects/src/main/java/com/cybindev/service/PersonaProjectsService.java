package com.cybindev.service;

import java.util.List;

import com.cybindev.domain.ProyectoResponseDTO;

public interface PersonaProjectsService<O, I> {
  O crearPersonaProject(I personaProject);

  O obtenerPersonaProjectPorId(Long id);

  List<ProyectoResponseDTO> obtenerProjectsPorPersonaId(Long personaId);

}
