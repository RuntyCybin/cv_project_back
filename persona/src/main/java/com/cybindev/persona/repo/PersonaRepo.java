package com.cybindev.persona.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cybindev.persona.domain.Persona;

@Repository
public interface PersonaRepo extends JpaRepository<Persona, Long> {

}
