package com.cybindev.skills.domain;

import org.springframework.stereotype.Component;

@Component
public class SkillMapper {
  public Skill toEntity(SkillRequestDTO dto) {
    if (dto == null) {
      return null;
    }

    Skill skill = new Skill();
    skill.setTitulo(dto.titulo());
    skill.setEsHighlight(dto.esHighlight());
    return skill;
  }

  public SkillResponseDTO toDTO(Skill skill) {
    if (skill == null) {
      return null;
    }

    return new SkillResponseDTO(skill.getId(), skill.getTitulo(), skill.getEsHighlight());
  }
}
