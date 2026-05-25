package com.cybindev.socials.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cybindev.socials.domain.Social;
import com.cybindev.socials.domain.SocialMapper;
import com.cybindev.socials.domain.SocialRequestDTO;
import com.cybindev.socials.domain.SocialResponseDTO;
import com.cybindev.socials.repo.SocialsRepo;
import com.cybindev.socials.service.SocialsService;

@Service
public class SocialServiceImpl implements SocialsService<SocialResponseDTO, SocialRequestDTO> {

  private final Logger logger = LoggerFactory.getLogger(SocialServiceImpl.class);
  private final SocialsRepo repo;
  private final SocialMapper mapper;

  public SocialServiceImpl(SocialsRepo repo, SocialMapper mapper) {
    this.repo = repo;
    this.mapper = mapper;
  }

  @Override
  public SocialResponseDTO crearRedSocial(SocialRequestDTO redSocial) {
    logger.info("Creating social media platform: {}",
        Objects.requireNonNull(redSocial, "request can not be null"));

    Social savedSocial = repo.save(Objects.requireNonNull(this.mapper.toEntity(redSocial),
        "social entity can not be null"));

    return this.mapper.toResponseDTO(savedSocial);
  }

  @Override
  public SocialResponseDTO obtenerRedSocialPorId(Long id) {
    if (id <= 0) {
      logger.warn("Invalid ID provided: {}", id);
      throw new IllegalArgumentException("ID must be positive");
    }
    logger.info("Fetching social media platform by ID: {}", id);

    return repo.findById(id)
        .map(social -> this.mapper.toResponseDTO(
            Objects.requireNonNull(social, "social entity can not be null")))
        .orElseThrow(() -> {
          logger.warn("No social media platform found with ID: {}", id);
          return new RuntimeException("No se ha recogido la red social con ID: " + id);
        });
  }

  @Override
  public List<SocialResponseDTO> listarRedesSociales() {
    logger.info("Listing all social media platforms");

    List<Social> socials = repo.findAll();
    if (socials.isEmpty()) {
      logger.warn("No social media platforms found in the database");
      throw new RuntimeException("No se han recogido redes sociales");
    }

    return socials.stream()
        .map(social -> this.mapper.toResponseDTO(
            Objects.requireNonNull(social, "social entity can not be null")))
        .toList();
  }

}
