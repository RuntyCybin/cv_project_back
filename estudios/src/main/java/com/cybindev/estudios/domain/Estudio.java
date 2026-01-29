package com.cybindev.estudios.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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

  // para no seteat los timestamps manualmente
  @PrePersist
  protected void onCreate() {
    this.createdAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

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

  public void setId(Long id) {
    this.id = id;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public void setInstitucion(String institucion) {
    this.institucion = institucion;
  }

  public void setPeriodo(String periodo) {
    this.periodo = periodo;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public void setCursos(String cursos) {
    this.cursos = cursos;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Estudio() {
  }
}
