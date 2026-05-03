package com.cybindev.cursos.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cybindev.cursos.domain.CursoResponseDTO;
import com.cybindev.cursos.domain.CursosResponseMapper;
import com.cybindev.cursos.domain.PersonaCurso;
import com.cybindev.cursos.domain.PersonaCursoRequestDTO;
import com.cybindev.cursos.domain.PersonaCursoRequestMapper;
import com.cybindev.cursos.domain.PersonaCursoResponseDTO;
import com.cybindev.cursos.domain.PersonaCursoResponseMapper;
import com.cybindev.cursos.repo.CursoRepo;
import com.cybindev.cursos.repo.PersonaCursoRepo;
import com.cybindev.cursos.service.PersonaCursoService;

@Service
public class PersonaCursoServiceImpl implements PersonaCursoService<PersonaCursoResponseDTO, PersonaCursoRequestDTO> {
  private final Logger logger = LoggerFactory.getLogger(PersonaCursoServiceImpl.class);
  private final PersonaCursoRepo repo;
  private final CursoRepo cursoRepo;
  private final CursosResponseMapper cursoResponseMapper;
  private final PersonaCursoRequestMapper requestMapper;
  private final PersonaCursoResponseMapper responseMapper;

  public PersonaCursoServiceImpl(PersonaCursoRepo repo,
      CursoRepo cursoRepo,
      CursosResponseMapper cursoResponseMapper,
      PersonaCursoRequestMapper requestMapper,
      PersonaCursoResponseMapper responseMapper) {
    this.repo = repo;
    this.cursoRepo = cursoRepo;
    this.requestMapper = requestMapper;
    this.responseMapper = responseMapper;
    this.cursoResponseMapper = cursoResponseMapper;
  }

  @Override
  public PersonaCursoResponseDTO crearPersonaCurso(PersonaCursoRequestDTO personaCurso) {
    logger.info("Creando una nueva relacion persona-curso en la base de datos");

    return Optional.ofNullable(personaCurso)
        .map(this.requestMapper::toEntity)
        .map(this.repo::save)
        .map(this.responseMapper::toDto)
        .orElseThrow(() -> new IllegalArgumentException("El DTO personaCurso no puede ser nulo"));

  }

  @Override
  public PersonaCursoResponseDTO obtenerPersonaCursoPorId(Long id) {
    logger.info("Obteniendo relacion persona-curso por ID: {}", id);
    if (id <= 0) {
      throw new IllegalArgumentException("El ID debe ser mayor que 0");
    }
    return this.repo.findById(id)
        .map(this.responseMapper::toDto)
        .orElseThrow(() -> new RuntimeException("PersonaCurso no encontrado con id: " + id));
  }

  @Override
  public List<CursoResponseDTO> obtenerCursosPorPersonaId(Long personaId) {
    if (personaId <= 0) {
      throw new IllegalArgumentException("El ID de persona debe ser mayor que 0");
    }
    logger.info("Obteniendo relacion persona-curso por ID de persona: {}", personaId);

    List<PersonaCurso> relaciones = this.repo.findByPersonaId(personaId);
    if (relaciones.isEmpty()) {
      throw new RuntimeException("No se encontraron relaciones para la persona con id: " + personaId);
    }
    return relaciones.stream()
        .map(pc -> {
          Long cursoId = Objects.requireNonNull(pc.getCursoId());
          return this.cursoRepo.findById(cursoId)
              .map(this.cursoResponseMapper::toDto)
              .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + cursoId));
        })
        .toList();
  }

}