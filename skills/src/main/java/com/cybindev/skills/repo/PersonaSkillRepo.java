package com.cybindev.skills.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cybindev.skills.domain.PersonaSkill;

public interface PersonaSkillRepo extends JpaRepository<PersonaSkill, Long> {

  List<PersonaSkill> findByPersonaId(Long personaId);

}
