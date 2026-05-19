package com.cybindev.skills.domain;

import org.springframework.stereotype.Component;

@Component
public class PersonaSkillMapper {
  public PersonaSkill toEntity(PersonaSkillRequestDTO dto) {
    if (dto == null) {
      return null;
    }

    PersonaSkill personaSkill = new PersonaSkill();
    personaSkill.setPersonaId(dto.personaId());
    personaSkill.setSkillId(dto.skillId());
    return personaSkill;
  }

  public PersonaSkillResponseDTO toDTO(PersonaSkill personaSkill) {
    if (personaSkill == null) {
      return null;
    }

    return new PersonaSkillResponseDTO(
        personaSkill.getId(),
        personaSkill.getPersonaId(),
        personaSkill.getSkillId());
  }
}
