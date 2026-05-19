package com.cybindev.skills.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "persona_skill")
public class PersonaSkill {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "persona_id", nullable = false)
  private Long personaId;

  @Column(name = "skill_id", nullable = false)
  private Long skillId;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  // para no seteat los timestamps manualmente
  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }

  public PersonaSkill() {
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

  public Long getSkillId() {
    return skillId;
  }

  public void setSkillId(Long skillId) {
    this.skillId = skillId;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

}
