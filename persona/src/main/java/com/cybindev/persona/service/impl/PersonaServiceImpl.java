package com.cybindev.persona.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
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
  private final PersonaRepo personaRepo;
  private final PersonaRequestMapper personaRequestMapper;
  private final PersonaResponseMapper personaResponseMapper;

  public PersonaServiceImpl(PersonaRepo personalRepo,
      PersonaRequestMapper personaRequestMapper,
      PersonaResponseMapper personaResponseMapper) {
    this.personaRepo = personalRepo;
    this.personaRequestMapper = personaRequestMapper;
    this.personaResponseMapper = personaResponseMapper;
  }

  @Override
  @CachePut(value = "personaCache", key = "#result.id")
  public PersonaResponseDTO crearPersona(PersonaRequestDTO personaDto) {
    logger.info("Servicio crear persona");

    if (personaDto != null) {
      Persona persona = this.personaRequestMapper.toPersona(personaDto);
      if (persona != null) {
        Persona saved = this.personaRepo.save(persona);
        return this.personaResponseMapper.toDTO(saved);
      } else {
        logger.warn("No se pudo mapear la solicitud de persona a una entidad Persona");
        throw new RuntimeException("Error al crear persona: datos inválidos");
      }
    } else {
      logger.warn("No se proporcionaron datos para crear una persona válida");
      throw new RuntimeException("Datos inválidos para crear persona");
    }
  }

  @Override
  @Cacheable(value = "personaCache", key = "#id")
  public PersonaResponseDTO obtenerPersonaPorId(Long id) {
    logger.info("Servicio obtener persona por id: " + id);
    if (id > 0L) {
      Persona persona = this.personaRepo.findById(id)
          .orElseThrow(() -> new RuntimeException("Persona no encontrada con id: " + id));
      return this.personaResponseMapper.toDTO(persona);
    } else {
      logger.error("ERROR: id debe de ser un numero entero");
      throw new RuntimeException("ERROR: id debe de ser un numero entero");
    }
  }

  @Override
  public List<PersonaResponseDTO> listarPersona() {
    logger.info("Servicio listar personas");
    List<Persona> personas = this.personaRepo.findAll();
    return personas.stream()
        .map(this.personaResponseMapper::toDTO)
        .toList();
  }

  @Override
  @CacheEvict(value = "personaCache", allEntries = true)
  public void eliminarPersona(PersonaRequestDTO dtoPersona) {
    logger.info("Servicio para eliminar datos de la persona y borrarlos de la cache");

    if (null != dtoPersona) {
      Persona persona = personaRequestMapper.toPersona(dtoPersona);
      if (null != persona) {
        this.personaRepo.delete(persona);
      } else {
        logger.error("ERROR: mapper fallo al convertir DTO a Entity");
        throw new RuntimeException("ERROR: mapper fallo al convertir DTO a Entity");
      }
    } else {
      logger.error("DTO de persona es nulo");
      throw new RuntimeException("DTO de persona es nulo");
    }
  }

}
