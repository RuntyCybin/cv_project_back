package com.cybindev.cursos.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cybindev.cursos.domain.Curso;
import com.cybindev.cursos.domain.CursoRequestDTO;
import com.cybindev.cursos.domain.CursoResponseDTO;
import com.cybindev.cursos.domain.CursosRequestMapper;
import com.cybindev.cursos.domain.CursosRequestMapperImpl;
import com.cybindev.cursos.domain.CursosResponseMapper;
import com.cybindev.cursos.domain.CursosResponseMapperImpl;
import com.cybindev.cursos.repo.CursoRepo;
import com.cybindev.cursos.service.CursoService;

@Service
public class CursoServiceImpl implements CursoService<CursoResponseDTO, CursoRequestDTO> {

  private final CursoRepo repo;
  private final CursosRequestMapper requestMapper;
  private final CursosResponseMapper responseMapper;

  public CursoServiceImpl(CursoRepo repo) {
    this.repo = repo;
    this.requestMapper = new CursosRequestMapperImpl();
    this.responseMapper = new CursosResponseMapperImpl();
  }

  @Override
  public CursoResponseDTO crearCurso(CursoRequestDTO curso) {
    System.out.println("Creando un nuevo curso en la base de datos");
    final Curso nuevoCurso = this.requestMapper.toEntity(curso);
    final Curso guardado = this.repo.save(nuevoCurso);
    if (guardado == null) {
      throw new RuntimeException("El servicio ha fallado al guardar el curso");
    }

    return this.responseMapper.toDto(guardado);
  }

  @Override
  public CursoResponseDTO obtenerCursoPorId(Long id) {
    System.out.println("Obteniendo curso con id: " + id);
    final Curso curso = this.repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + id));
    return this.responseMapper.toDto(curso);
  }

  @Override
  public List<CursoResponseDTO> listarCursos() {
    System.out.println("Listado de cursos de la base de datos");
    final List<Curso> cursos = this.repo.findAll();
    if (cursos.size() == 0) {
      throw new RuntimeException("No se han recogido cursos");
    }
    return cursos.stream()
            .map(this.responseMapper::toDto)
            .toList();
  }

}
