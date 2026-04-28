package com.cybindev.cursos.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cybindev.cursos.domain.PersonaCurso;

public interface PersonaCursoRepo extends JpaRepository<PersonaCurso, Long> {
  List<PersonaCurso> findByPersonaId(Long personaId);

  List<PersonaCurso> findByCursoId(Long cursoId);

}
