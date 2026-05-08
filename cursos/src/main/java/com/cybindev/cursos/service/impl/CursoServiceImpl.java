package com.cybindev.cursos.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import com.cybindev.cursos.domain.Curso;
import com.cybindev.cursos.domain.CursoRequestDTO;
import com.cybindev.cursos.domain.CursoResponseDTO;
import com.cybindev.cursos.domain.CursosRequestMapper;
import com.cybindev.cursos.domain.CursosResponseMapper;
import com.cybindev.cursos.repo.CursoRepo;
import com.cybindev.cursos.service.CursoService;

@Service
public class CursoServiceImpl implements CursoService<CursoResponseDTO, CursoRequestDTO> {

  private static final Logger logger = LoggerFactory.getLogger(CursoServiceImpl.class);
  private final CursoRepo repo;
  private final CursosRequestMapper requestMapper;
  private final CursosResponseMapper responseMapper;

  public CursoServiceImpl(CursoRepo repo, CursosRequestMapper requestMapper,
      CursosResponseMapper responseMapper) {
    this.repo = repo;
    this.requestMapper = requestMapper;
    this.responseMapper = responseMapper;
  }

  @Override
  public CursoResponseDTO crearCurso(CursoRequestDTO curso) {
    logger.info("Creando un nuevo curso en la base de datos");
    final Curso guardado = this.repo.save(
        Objects.requireNonNull(
            this.requestMapper.toEntity(curso)));
    logger.info("Curso guardado correctamente: " + guardado);
    return this.responseMapper.toDto(guardado);
  }

  @Override
  public CursoResponseDTO obtenerCursoPorId(Long id) {
    logger.info("Obteniendo curso con id: " + id);
    final Curso curso = this.repo.findById(Objects.requireNonNull(id))
        .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + id));
    return this.responseMapper.toDto(curso);
  }

  @Override
  public List<CursoResponseDTO> listarCursos() {
    logger.info("Listado de cursos de la base de datos");
    final List<Curso> cursos = this.repo.findAll();
    if (cursos.size() == 0) {
      throw new RuntimeException("No se han recogido cursos");
    }
    return cursos.stream()
        .map(this.responseMapper::toDto)
        .toList();
  }

}
