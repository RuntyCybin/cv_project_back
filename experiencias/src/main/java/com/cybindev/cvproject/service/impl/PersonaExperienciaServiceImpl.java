package com.cybindev.cvproject.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cybindev.cvproject.domain.Experiencia;
import com.cybindev.cvproject.domain.ExperienciaResponseDTO;
import com.cybindev.cvproject.domain.ExperienciaResponseMapper;
import com.cybindev.cvproject.domain.PersonaExperiencia;
import com.cybindev.cvproject.domain.PersonaExperienciaRequestDTO;
import com.cybindev.cvproject.domain.PersonaExperienciaRequestMapper;
import com.cybindev.cvproject.domain.PersonaExperienciaResponseDTO;
import com.cybindev.cvproject.domain.PersonaExperienciaResponseMapper;
import com.cybindev.cvproject.repository.ExperienciaRepo;
import com.cybindev.cvproject.repository.PersonaExperienciaRepo;
import com.cybindev.cvproject.service.PersonaExperienciaService;

@Service
public class PersonaExperienciaServiceImpl
    implements PersonaExperienciaService<PersonaExperienciaResponseDTO, PersonaExperienciaRequestDTO> {

  private final Logger logger = LoggerFactory.getLogger(PersonaExperienciaServiceImpl.class);
  private final PersonaExperienciaRequestMapper requestMapper;
  private final PersonaExperienciaResponseMapper responseMapper;
  private final PersonaExperienciaRepo repo;
  private final ExperienciaRepo experienciaRepo;
  private final ExperienciaResponseMapper experienciaResponseMapper;

  public PersonaExperienciaServiceImpl(PersonaExperienciaRequestMapper requestMapper,
      PersonaExperienciaResponseMapper responseMapper,
      PersonaExperienciaRepo repo,
      ExperienciaRepo experienciaRepo,
      ExperienciaResponseMapper experienciaResponseMapper) {
    this.requestMapper = requestMapper;
    this.responseMapper = responseMapper;
    this.repo = repo;
    this.experienciaRepo = experienciaRepo;
    this.experienciaResponseMapper = experienciaResponseMapper;
  }

  @Override
  public PersonaExperienciaResponseDTO crearPersonaExperiencia(PersonaExperienciaRequestDTO personaExperiencia) {
    logger.info("Creando experiencia para persona: {}", personaExperiencia.personaId());

    return Optional.ofNullable(personaExperiencia)
        .map(this.requestMapper::toEntity)
        .map(repo::save)
        .map(this.responseMapper::toDTO)
        .orElseThrow(() -> new RuntimeException("El DTO personaExperiencia no puede ser nulo"));
  }

  @Override
  public PersonaExperienciaResponseDTO obtenerPersonaExperienciaPorId(Long id) {
    return this.repo.findById(id)
        .map(this.responseMapper::toDTO)
        .orElseThrow(() -> new RuntimeException("No se encontró la experiencia con id: " + id));
  }

  @Override
  public List<ExperienciaResponseDTO> obtenerExperienciasPorPersonaId(Long personaId) {
    List<PersonaExperiencia> personaExperiencias = this.repo.findByPersonaId(personaId);

    if (personaExperiencias.isEmpty()) {
      throw new RuntimeException("No se encontraron experiencias para la persona con id: " + personaId);
    }

    List<Long> experienciaIds = personaExperiencias.stream()
        .map(pe -> pe.getExperienciaId())
        .toList();

    List<Experiencia> experiencias = this.experienciaRepo.findAllById(experienciaIds);

    return experiencias.stream()
        .map(this.experienciaResponseMapper::toDto)
        .toList();
  }

}
