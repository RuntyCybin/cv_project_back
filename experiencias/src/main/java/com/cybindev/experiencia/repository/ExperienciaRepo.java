package com.cybindev.experiencia.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cybindev.experiencia.domain.Experiencia;

@Repository
public interface ExperienciaRepo extends JpaRepository<Experiencia, Long> {

  Optional<Experiencia> findByPuestoAndEmpresa(String puesto, String empresa);

}
