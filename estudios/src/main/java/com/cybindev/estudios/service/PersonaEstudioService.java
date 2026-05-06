package com.cybindev.estudios.service;

import java.util.List;

import com.cybindev.estudios.domain.EstudioResponseDTO;

public interface PersonaEstudioService<O, I> {
  O crearPersonaEstudio(I personaEstudio);

  O obtenerPersonaEstudioPorId(Long id);

  List<EstudioResponseDTO> obtenerEstudiosPorPersonaId(Long personaId);

}
