package com.cybindev.autenticacion.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "persona_autenticacion")
public class PersonaAutenticacion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "persona_id", nullable = false)
  private Long personaId;

  @Column(name = "autenticacion_id", nullable = false)
  private Long autenticacionId;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }

  public PersonaAutenticacion() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getPersonaId() {
    return personaId;
  }

  public void setPersonaId(Long personaId) {
    this.personaId = personaId;
  }

  public Long getAutenticacionId() {
    return autenticacionId;
  }

  public void setAutenticacionId(Long autenticacionId) {
    this.autenticacionId = autenticacionId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }
}
