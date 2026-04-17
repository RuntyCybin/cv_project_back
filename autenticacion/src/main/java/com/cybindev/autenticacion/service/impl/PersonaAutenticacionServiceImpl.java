package com.cybindev.autenticacion.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cybindev.autenticacion.domain.PersonaAutenticacion;
import com.cybindev.autenticacion.domain.PersonaAutenticacionRequestDTO;
import com.cybindev.autenticacion.domain.PersonaAutenticacionRequestMapper;
import com.cybindev.autenticacion.domain.PersonaAutenticacionResponseDTO;
import com.cybindev.autenticacion.domain.PersonaAutenticacionResponseMapper;
import com.cybindev.autenticacion.repo.PersonaAutenticacionRepo;
import com.cybindev.autenticacion.service.PersonaAutenticacionService;

@Service
public class PersonaAutenticacionServiceImpl
    implements PersonaAutenticacionService<PersonaAutenticacionResponseDTO, PersonaAutenticacionRequestDTO> {

  @Value("${app.title}")
  private String title;

  private static final Logger logger = LoggerFactory.getLogger(PersonaAutenticacionService.class);
  private final PersonaAutenticacionResponseMapper personaAutenticacionResponseMapper;
  private final PersonaAutenticacionRequestMapper personaAutenticacionRequestMapper;
  private final PersonaAutenticacionRepo personaAutenticacionRepository;

  public PersonaAutenticacionServiceImpl(PersonaAutenticacionRepo personaAutenticacionRepository,
      PersonaAutenticacionResponseMapper personaAutenticacionResponseMapper,
      PersonaAutenticacionRequestMapper personaAutenticacionRequestMapper) {
    this.personaAutenticacionRepository = personaAutenticacionRepository;
    this.personaAutenticacionResponseMapper = personaAutenticacionResponseMapper;
    this.personaAutenticacionRequestMapper = personaAutenticacionRequestMapper;
  }

  @Override
  public PersonaAutenticacionResponseDTO crearPersonaAutenticacion(
      PersonaAutenticacionRequestDTO personaAutenticacion) {
    logger.info("Creando una nueva relación autenticación - persona en {}", title);

    if (personaAutenticacion != null) {
      PersonaAutenticacion persona = personaAutenticacionRequestMapper
          .toPersonaAutenticacion(personaAutenticacion);

      if (persona != null) {
        return personaAutenticacionResponseMapper
            .toPersonaAutenticacionResponseDTO(personaAutenticacionRepository.save(persona));
      } else {
        logger.warn(
            "No se pudo mapear la solicitud de relación persona - autenticación a una entidad PersonaAutenticacion");
        throw new RuntimeException("Error al crear relación persona - autenticación: datos inválidos");
      }

    } else {
      logger.warn("No se proporcionaron datos para crear una relación persona - autenticación válida");
      throw new RuntimeException("Datos inválidos para crear una relación persona - autenticación");
    }

  }

  @Override
  public PersonaAutenticacionResponseDTO obtenerPersonaAutenticacionPorId(Long id) {
    logger.info("Obteniendo la relación autenticación - persona en {}", title);

    if (id > 0) {
      logger.info("ID válida para obtener la relación autenticación - persona: {}", id);
      return personaAutenticacionResponseMapper.toPersonaAutenticacionResponseDTO(
          personaAutenticacionRepository.findById(id).orElseThrow(
              () -> new RuntimeException("No se encontró la relacion autenticación - persona con ID: " + id)));
    } else {
      logger.warn("ID inválida para obtener la relación autenticación - persona: {}", id);
      throw new RuntimeException("ID inválida para obtener la relación autenticación - persona: " + id);
    }
  }

  /*
   * Busca la autenticación asociada a una persona por su ID.
   * Si no se encuentra, lanza una excepción indicando que no se encontró la
   * autenticación para la persona con el ID proporcionado.
   */
  @Override
  public PersonaAutenticacionResponseDTO obtenerPersonaAutenticacionPorPersonaId(Long personaId) {
    logger.info("Obteniendo la relación autenticación - persona para la persona con ID: {} en {}", personaId, title);

    return personaAutenticacionResponseMapper.toPersonaAutenticacionResponseDTO(
        personaAutenticacionRepository.findByPersonaId(personaId)
            .orElseThrow(
                () -> new RuntimeException(
                    "No se encontró la relacion autenticación - persona para la persona con ID: "
                        + personaId)));
  }

  /*
   * Busca la persona asociada a una autenticación por su ID.
   * Si no se encuentra, lanza una excepción indicando que no se encontró la
   * persona para la autenticación con el ID proporcionado.
   */
  @Override
  public PersonaAutenticacionResponseDTO obtenerPersonaAutenticacionPorAutenticacionId(Long autenticacionId) {
    logger.info("Obteniendo la relación autenticación - persona para la autenticación con ID: {} en {}",
        autenticacionId, title);

    return personaAutenticacionResponseMapper.toPersonaAutenticacionResponseDTO(
        personaAutenticacionRepository.findByAutenticacionId(autenticacionId)
            .orElseThrow(
                () -> new RuntimeException(
                    "No se encontró la relacion autenticación - persona para la autenticación con ID: "
                        + autenticacionId)));
  }

}
