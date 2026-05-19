package com.cybindev.skills.service;

import java.util.List;

public interface SkillService<O, I> {
  O crearSkill(I skill);

  O obtenerSkillPorId(Long id);

  List<O> listarSkills();

}
