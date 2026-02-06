package com.cybindev.cursos.service;

import java.util.List;

public interface CursoService<O, I> {
  O crearCurso(I curso);

  O obtenerCursoPorId(Long id);

  List<O> listarCursos();

}
