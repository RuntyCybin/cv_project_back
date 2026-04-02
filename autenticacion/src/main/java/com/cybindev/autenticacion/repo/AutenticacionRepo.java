package com.cybindev.autenticacion.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cybindev.autenticacion.domain.Autenticacion;

public interface AutenticacionRepo extends JpaRepository<Autenticacion, Long> {

  Optional<Autenticacion> findByLoginAndPassword(String login, String password);

  Optional<Autenticacion> findByLogin(String login);
}
