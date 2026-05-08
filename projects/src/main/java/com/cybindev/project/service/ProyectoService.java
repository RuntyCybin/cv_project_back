package com.cybindev.project.service;

import java.util.List;

public interface ProyectoService<O, I> {
  O crearProyecto(I proyecto);

  O obtenerProyectoPorId(Long id);

  List<O> listarProyectos();
}
