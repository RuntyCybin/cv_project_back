package com.cybindev.project.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cybindev.project.domain.PersonaProyecto;
import com.cybindev.project.domain.PersonaProyectoRequestDTO;
import com.cybindev.project.domain.Project;
import com.cybindev.project.domain.PersonaProyectoRequestMapper;
import com.cybindev.project.domain.PersonaProyectoResponseDTO;
import com.cybindev.project.domain.PersonaProyectoResponseMapper;
import com.cybindev.project.domain.ProyectResponseMapper;
import com.cybindev.project.domain.ProyectoResponseDTO;
import com.cybindev.project.repo.PersonaProyectoRepo;
import com.cybindev.project.repo.ProyectoRepo;
import com.cybindev.project.service.PersonaProjectsService;

@Service
public class PersonaProjectsServiceImpl
    implements PersonaProjectsService<PersonaProyectoResponseDTO, PersonaProyectoRequestDTO> {

  private final Logger logger = LoggerFactory.getLogger(PersonaProjectsServiceImpl.class);
  private final PersonaProyectoRepo repo;
  private final ProyectoRepo proyectoRepo;
  private final PersonaProyectoRequestMapper requestMapper;
  private final PersonaProyectoResponseMapper responseMapper;
  private final ProyectResponseMapper proyectoResponseMapper;

  public PersonaProjectsServiceImpl(PersonaProyectoRepo repo, PersonaProyectoRequestMapper requestMapper,
      PersonaProyectoResponseMapper responseMapper, ProyectResponseMapper proyectoResponseMapper,
      ProyectoRepo proyectoRepo) {
    this.repo = repo;
    this.requestMapper = requestMapper;
    this.responseMapper = responseMapper;
    this.proyectoResponseMapper = proyectoResponseMapper;
    this.proyectoRepo = proyectoRepo;
  }

  @Override
  public PersonaProyectoResponseDTO crearPersonaProject(PersonaProyectoRequestDTO personaProject) {
    if (null == personaProject) {
      throw new IllegalArgumentException("El DTO personaProject no puede ser nulo");
    }
    logger.info("Creando una nueva relacion persona-project en la base de datos");

    return Optional.ofNullable(personaProject)
        .map(this.requestMapper::toEntity)
        .map(this.repo::save)
        .map(this.responseMapper::toDTO)
        .orElseThrow(() -> new IllegalArgumentException("El DTO personaProject no puede ser nulo"));
  }

  @Override
  public PersonaProyectoResponseDTO obtenerPersonaProjectPorId(Long id) {
    if (id <= 0) {
      throw new IllegalArgumentException("El ID debe ser mayor que 0");
    }
    logger.info("Obteniendo relacion persona-project por ID: {}", id);

    return Optional.ofNullable(id)
        .filter(i -> i > 0)
        .flatMap(this.repo::findById)
        .map(this.responseMapper::toDTO)
        .orElseThrow(() -> new IllegalArgumentException("El ID debe ser mayor que 0 y el proyecto debe existir"));
  }

  @Override
  public List<ProyectoResponseDTO> obtenerProjectsPorPersonaId(Long personaId) {
    if (personaId <= 0) {
      throw new IllegalArgumentException("El ID de la persona debe ser mayor que 0");
    }
    logger.info("Obteniendo proyectos por ID de persona: {}", personaId);

    List<PersonaProyecto> personaProyectos = this.repo.findByPersonaId(personaId);
    if (personaProyectos.isEmpty()) {
      logger.warn("No se encontraron proyectos para la persona con ID: {}", personaId);
      throw new RuntimeException("No se encontraron proyectos para la persona con ID: " + personaId);
    }

    List<Long> proyectoIds = personaProyectos.stream()
        .map(pp -> pp.getProyectoId())
        .collect(Collectors.toList());
    List<Project> proyectos = this.proyectoRepo.findAllById(Objects.requireNonNull(proyectoIds));
    return proyectos.stream()
        .map(p -> this.proyectoResponseMapper.toDto(p))
        .collect(Collectors.toList());
  }

}
