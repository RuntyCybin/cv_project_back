package com.cybindev.skills.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cybindev.skills.domain.PersonaSkill;
import com.cybindev.skills.domain.PersonaSkillMapper;
import com.cybindev.skills.domain.PersonaSkillRequestDTO;
import com.cybindev.skills.domain.PersonaSkillResponseDTO;
import com.cybindev.skills.domain.Skill;
import com.cybindev.skills.domain.SkillMapper;
import com.cybindev.skills.domain.SkillRequestDTO;
import com.cybindev.skills.domain.SkillResponseDTO;
import com.cybindev.skills.repo.PersonaSkillRepo;
import com.cybindev.skills.repo.SkillRepo;
import com.cybindev.skills.service.PersonaSkillService;

@Service
public class PersonaSkillServiceImpl implements PersonaSkillService<PersonaSkillResponseDTO, PersonaSkillRequestDTO> {

  private final Logger logger = LoggerFactory.getLogger(PersonaSkillServiceImpl.class);
  private final PersonaSkillRepo repo;
  private final SkillRepo skillRepo;
  private final PersonaSkillMapper mapper;
  private final SkillMapper skillMapper;

  public PersonaSkillServiceImpl(PersonaSkillRepo repo,
      SkillRepo skillRepo,
      PersonaSkillMapper mapper,
      SkillMapper skillMapper) {
    this.repo = repo;
    this.skillRepo = skillRepo;
    this.mapper = mapper;
    this.skillMapper = skillMapper;
  }

  @Override
  public PersonaSkillResponseDTO crearPersonaSkill(PersonaSkillRequestDTO personaSkill) {
    logger.info("Creando personaSkill en la base de datos");
    if (null == personaSkill) {
      throw new RuntimeException("El servicio ha fallado al recoger el personaSkill");
    }

    PersonaSkill savedPersonaSkill = this.repo.save(
        Objects.requireNonNull(
            this.mapper.toEntity(personaSkill), "El mapper retornó null"));

    return this.mapper.toDTO(savedPersonaSkill);
  }

  @Override
  public List<SkillResponseDTO> obtenerSkillsPorPersonaId(Long id) {
    if (id <= 0) {
      logger.info("Obteniendo skills por personaId: " + id);
    }

    List<PersonaSkill> personaSkills = this.repo.findByPersonaId(id);
    if (personaSkills.isEmpty()) {
      throw new RuntimeException("No se han recogido skills para la persona con id: " + id);
    }

    List<Long> skillIds = personaSkills.stream()
        .map(ps -> ps.getSkillId())
        .collect(java.util.stream.Collectors.toList());

    List<Skill> skills = this.skillRepo
        .findAllById(Objects.requireNonNull(skillIds, "La lista de skillIds no puede ser null"));

    return skills.stream().map(this.skillMapper::toDTO).toList();
  }

}
