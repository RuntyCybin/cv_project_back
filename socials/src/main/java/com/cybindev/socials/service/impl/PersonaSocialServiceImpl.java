package com.cybindev.socials.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cybindev.socials.domain.PersonaSocial;
import com.cybindev.socials.domain.PersonaSocialMapper;
import com.cybindev.socials.domain.PersonaSocialRequestDTO;
import com.cybindev.socials.domain.PersonaSocialResponseDTO;
import com.cybindev.socials.domain.Social;
import com.cybindev.socials.domain.SocialMapper;
import com.cybindev.socials.domain.SocialResponseDTO;
import com.cybindev.socials.repo.PersonaSocialRepo;
import com.cybindev.socials.repo.SocialsRepo;
import com.cybindev.socials.service.PersonaSocialsService;

@Service
public class PersonaSocialServiceImpl
    implements PersonaSocialsService<PersonaSocialResponseDTO, PersonaSocialRequestDTO> {

  private final Logger logger = LoggerFactory.getLogger(PersonaSocialServiceImpl.class);
  private final PersonaSocialRepo repo;
  private final PersonaSocialMapper mapper;
  private final SocialMapper socialMapper;
  private final SocialsRepo socialsRepo;

  public PersonaSocialServiceImpl(PersonaSocialRepo repo,
      PersonaSocialMapper mapper, SocialMapper socialMapper, SocialsRepo socialsRepo) {
    this.repo = repo;
    this.mapper = mapper;
    this.socialMapper = socialMapper;
    this.socialsRepo = socialsRepo;
  }

  @Override
  public PersonaSocialResponseDTO crearPersonaRedSocial(PersonaSocialRequestDTO personaRedSocial) {
    Objects.requireNonNull(personaRedSocial, "request can not be null");
    logger.info("Servicio de crear relacion persona-social: {}", personaRedSocial);

    PersonaSocial personaSocial = repo.save(this.mapper.toEntity(personaRedSocial));

    return this.mapper.toResponseDTO(personaSocial);
  }

  @Override
  public PersonaSocialResponseDTO obtenerPersonaRedSocialPorId(Long id) {
    if (id <= 0) {
      logger.warn("Invalid ID provided: {}", id);
      throw new IllegalArgumentException("ID must be positive");
    }
    logger.info("Servicio de obtener relacion persona-social por ID: " + id);

    PersonaSocial personaSocial = this.repo.findById(id).orElseThrow(() -> {
      logger.warn("No persona-social relationship found with ID: {}", id);
      return new IllegalArgumentException("No persona-social relationship found with ID: " + id);
    });
    return this.mapper.toResponseDTO(personaSocial);
  }

  @Override
  public List<SocialResponseDTO> obtenerRedesSocialesPorPersonaId(Long personaId) {
    if (personaId <= 0) {
      logger.warn("Invalid persona ID provided: {}", personaId);
      throw new IllegalArgumentException("Persona ID must be positive");
    }
    logger.info("Servicio de obtener redes sociales por ID de persona: " + personaId);

    List<Long> socialIds = this.repo.findByPersonaId(personaId).stream()
        .map(personaSocial -> personaSocial.getSocialId())
        .toList();

    List<Social> socials = this.socialsRepo.findAllById(Objects.requireNonNull(socialIds, "Social IDs cannot be null"));

    return socials.stream().map(this.socialMapper::toResponseDTO).toList();
  }

}
