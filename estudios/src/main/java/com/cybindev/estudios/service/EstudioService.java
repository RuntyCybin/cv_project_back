package com.cybindev.estudios.service;

import java.util.List;

public interface EstudioService<O, I> {
  List<O> listarEstudios();

  O crearEstudio(I estudio);

  O obtenerEstudioPorId(Long id);
}
