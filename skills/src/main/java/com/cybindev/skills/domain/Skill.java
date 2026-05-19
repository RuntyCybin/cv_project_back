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
@Table(name = "skills")
public class Skill {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "titulo", nullable = false)
  private String titulo;

  @Column(name = "es_highlighted", nullable = false)
  private Boolean esHighlight;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  // para no seteat los timestamps manualmente
  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public Boolean getEsHighlight() {
    return esHighlight;
  }

  public void setEsHighlight(Boolean esHighlight) {
    this.esHighlight = esHighlight;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public Skill() {
  }

}
