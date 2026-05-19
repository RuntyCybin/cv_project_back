package com.cybindev.skills.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cybindev.skills.domain.SkillMapper;
import com.cybindev.skills.domain.SkillRequestDTO;
import com.cybindev.skills.domain.SkillResponseDTO;
import com.cybindev.skills.repo.SkillRepo;
import com.cybindev.skills.service.SkillService;

@Service
public class SkillServiceImpl implements SkillService<SkillResponseDTO, SkillRequestDTO> {

  private final Logger logger = LoggerFactory.getLogger(SkillServiceImpl.class);
  private final SkillRepo repo;
  private final SkillMapper mapper;

  public SkillServiceImpl(SkillRepo repo, SkillMapper mapper) {
    this.repo = repo;
    this.mapper = mapper;
  }

  @Override
  public SkillResponseDTO crearSkill(SkillRequestDTO skill) {
    logger.info("Creando skill en la base de datos");

    if (skill == null) {
      throw new RuntimeException("El servicio ha fallado al recoger el skill");
    }

    var entity = Objects.requireNonNull(this.mapper.toEntity(skill), "El mapper retornó null");
    return this.mapper.toDTO(this.repo.save(entity));

  }

  @Override
  public SkillResponseDTO obtenerSkillPorId(Long id) {
    logger.info("Obteniendo skill con id: " + id);

    if (id <= 0) {
      throw new IllegalArgumentException("El id debe ser un número positivo");
    }

    final var result = this.repo.findById(id)
        .orElseThrow(() -> new RuntimeException("El servicio ha fallado al recoger el skill"));
    return this.mapper.toDTO(result);
  }

  @Override
  public List<SkillResponseDTO> listarSkills() {
    logger.info("Listando skills");

    return this.repo.findAll().stream()
        .map(this.mapper::toDTO)
        .toList();
  }

}
