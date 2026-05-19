package com.cybindev.skills.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cybindev.skills.domain.Skill;

public interface SkillRepo extends JpaRepository<Skill, Long> {

}
