package com.cybindev.persona.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cybindev.persona.domain.Persona;

public interface PersonaRepo extends JpaRepository<Persona, Long> {

}
