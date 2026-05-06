package com.cybindev.estudios.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cybindev.estudios.domain.PersonaEstudio;

@Repository
public interface PersonaEstudioRepo extends JpaRepository<PersonaEstudio, Long> {
  List<PersonaEstudio> findByPersonaId(Long personaId);
}
