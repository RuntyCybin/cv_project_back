package com.cybindev.skills.service;

import java.util.List;

import com.cybindev.skills.domain.SkillResponseDTO;

public interface PersonaSkillService<O, I> {
  O crearPersonaSkill(I personaSkill);

  List<SkillResponseDTO> obtenerSkillsPorPersonaId(Long id);

}
