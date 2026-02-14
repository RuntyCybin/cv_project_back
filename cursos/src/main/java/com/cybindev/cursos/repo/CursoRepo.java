package com.cybindev.cursos.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cybindev.cursos.domain.Curso;

@Repository
public interface CursoRepo extends JpaRepository<Curso, Long> {

}
