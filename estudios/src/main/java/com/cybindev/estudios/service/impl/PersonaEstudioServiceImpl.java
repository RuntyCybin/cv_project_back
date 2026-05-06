package com.cybindev.estudios.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cybindev.estudios.domain.Estudio;
import com.cybindev.estudios.domain.EstudioResponseDTO;
import com.cybindev.estudios.domain.EstudioResponseMapper;
import com.cybindev.estudios.domain.PersonaEstudio;
import com.cybindev.estudios.domain.PersonaEstudioRequestDTO;
import com.cybindev.estudios.domain.PersonaEstudioRequestMapper;
import com.cybindev.estudios.domain.PersonaEstudioResponseDTO;
import com.cybindev.estudios.domain.PersonaEstudioResponseMapper;
import com.cybindev.estudios.repo.EstudiosRepo;
import com.cybindev.estudios.repo.PersonaEstudioRepo;
import com.cybindev.estudios.service.PersonaEstudioService;

@Service
public class PersonaEstudioServiceImpl
    implements PersonaEstudioService<PersonaEstudioResponseDTO, PersonaEstudioRequestDTO> {

  private final Logger logger = LoggerFactory.getLogger(PersonaEstudioServiceImpl.class);
  private final PersonaEstudioRepo repo;
  private final EstudiosRepo estudioRepo;
  private final EstudioResponseMapper estudioResponseMapper;
  private final PersonaEstudioRequestMapper requestMapper;
  private final PersonaEstudioResponseMapper responseMapper;

  public PersonaEstudioServiceImpl(PersonaEstudioRepo repo,
      EstudiosRepo estudioRepo,
      EstudioResponseMapper estudioResponseMapper,
      PersonaEstudioRequestMapper requestMapper,
      PersonaEstudioResponseMapper responseMapper) {
    this.repo = repo;
    this.estudioRepo = estudioRepo;
    this.estudioResponseMapper = estudioResponseMapper;
    this.requestMapper = requestMapper;
    this.responseMapper = responseMapper;
  }

  @Override
  public PersonaEstudioResponseDTO crearPersonaEstudio(PersonaEstudioRequestDTO personaEstudio) {
    if (null == personaEstudio) {
      throw new IllegalArgumentException("El DTO personaEstudio no puede ser nulo");
    }
    logger.info("Creando relacion persona-estudio para personaId: {} y estudioId: {}",
        personaEstudio.personaId(), personaEstudio.estudioId());

    return Optional.ofNullable(personaEstudio)
        .map(this.requestMapper::toEntity)
        .map(this.repo::save)
        .map(this.responseMapper::toDTO)
        .orElseThrow(() -> new IllegalArgumentException("El DTO personaEstudio no puede ser nulo"));
  }

  @Override
  public PersonaEstudioResponseDTO obtenerPersonaEstudioPorId(Long id) {
    if (id <= 0) {
      throw new IllegalArgumentException("El ID de persona debe ser un número positivo");
    }
    logger.info("Obteniendo relacion persona-estudio por ID: {}", id);

    return this.repo.findById(id)
        .map(this.responseMapper::toDTO)
        .orElseThrow(() -> new RuntimeException("No se encontró el estudio con id: " + id));
  }

  @Override
  public List<EstudioResponseDTO> obtenerEstudiosPorPersonaId(Long personaId) {
    if (personaId <= 0) {
      throw new IllegalArgumentException("El ID de persona debe ser un número positivo");
    }
    logger.info("Obteniendo estudios para persona con id: {}", personaId);

    List<PersonaEstudio> personaEstudios = this.repo.findByPersonaId(personaId);
    if (personaEstudios.isEmpty()) {
      logger.warn("No se encontraron estudios para la persona con id: {}", personaId);
      throw new RuntimeException("No se encontraron estudios para la persona con id: " + personaId);
    }

    List<Long> estudioIds = personaEstudios.stream()
        .map(pe -> pe.getEstudioId())
        .toList();

    List<Estudio> estudios = this.estudioRepo.findAllById(Objects.requireNonNull(estudioIds));
    return estudios.stream()
        .map(this.estudioResponseMapper::toDto)
        .toList();
  }

}
