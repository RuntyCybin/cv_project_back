package com.cybindev.cursos.service;

import java.util.List;

import com.cybindev.cursos.domain.CursoResponseDTO;

public interface PersonaCursoService<O, I> {
  O crearPersonaCurso(I personaCurso);

  O obtenerPersonaCursoPorId(Long id);

  List<CursoResponseDTO> obtenerCursosPorPersonaId(Long personaId);

}
