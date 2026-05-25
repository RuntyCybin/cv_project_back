package com.cybindev.socials.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cybindev.socials.domain.PersonaSocial;

public interface PersonaSocialRepo extends JpaRepository<PersonaSocial, Long> {

  List<PersonaSocial> findByPersonaId(Long personaId);
}
