package com.cybindev.estudios.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "estudios")
public class Estudio {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "titulo", nullable = false)
  private String titulo;

  @Column(name = "institucion", nullable = false)
  private String institucion;

  @Column(name = "periodo", nullable = false)
  private String periodo;

  @Column(name = "descripcion", columnDefinition = "TEXT", nullable = false)
  private String descripcion;

  @Column(name = "cursos", nullable = false)
  private String cursos;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  public Long getId() {
    return id;
  }

  public String getTitulo() {
    return titulo;
  }

  public String getInstitucion() {
    return institucion;
  }

  public String getPeriodo() {
    return periodo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public String getCursos() {
    return cursos;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public Estudio(Long id, String titulo, String institucion, String periodo, String descripcion, String cursos,
      LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.titulo = titulo;
    this.institucion = institucion;
    this.periodo = periodo;
    this.descripcion = descripcion;
    this.cursos = cursos;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}
