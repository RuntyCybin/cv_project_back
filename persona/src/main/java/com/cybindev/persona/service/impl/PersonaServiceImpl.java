package com.cybindev.persona.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cybindev.persona.domain.Persona;
import com.cybindev.persona.domain.PersonaRequestDTO;
import com.cybindev.persona.domain.PersonaRequestMapper;
import com.cybindev.persona.domain.PersonaResponseDTO;
import com.cybindev.persona.domain.PersonaResponseMapper;
import com.cybindev.persona.repo.PersonaRepo;
import com.cybindev.persona.service.PersonaService;

@Service
public class PersonaServiceImpl implements PersonaService<PersonaResponseDTO, PersonaRequestDTO> {

  @Value("${app.title}")
  private String title;

  private static final Logger logger = LoggerFactory.getLogger(PersonaServiceImpl.class);
  private final PersonaRepo personalRepo;
  private final PersonaRequestMapper personaRequestMapper;
  private final PersonaResponseMapper personaResponseMapper;

  public PersonaServiceImpl(PersonaRepo personalRepo, PersonaRequestMapper personaRequestMapper,
      PersonaResponseMapper personaResponseMapper) {
    this.personalRepo = personalRepo;
    this.personaRequestMapper = personaRequestMapper;
    this.personaResponseMapper = personaResponseMapper;
  }

  @Override
  @CachePut(value = "personaCache", key = "#result.id")
  public PersonaResponseDTO crearPersona(PersonaRequestDTO personaDto) {
    logger.info("Servicio crear persona");
    Persona persona = this.personaRequestMapper.toPersona(personaDto);
    Persona saved = this.personalRepo.save(persona);
    return this.personaResponseMapper.toDTO(saved);
  }

  @Override
  @Cacheable(value = "personaCache", key = "#id")
  public PersonaResponseDTO obtenerPersonaPorId(Long id) {
    logger.info("Servicio obtener persona por id: " + id);
    Persona persona = this.personalRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Persona no encontrada con id: " + id));
    return this.personaResponseMapper.toDTO(persona);
  }

  @Override
  public List<PersonaResponseDTO> listarPersona() {
    logger.info("Servicio listar personas");
    List<Persona> personas = this.personalRepo.findAll();
    return personas.stream()
        .map(this.personaResponseMapper::toDTO)
        .toList();
  }

}
