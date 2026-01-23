package com.cybindev.estudios.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cybindev.estudios.domain.Estudio;

@Repository
public interface EstudiosRepo extends JpaRepository<Estudio, Long> {

}
