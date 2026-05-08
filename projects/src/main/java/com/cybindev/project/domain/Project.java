package com.cybindev.project.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "proyectos")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "titulo", nullable = false)
  private String titulo;

  @Column(name = "periodo", nullable = false)
  private String periodo;

  @Column(name = "descripcion", nullable = false)
  private String descripcion;

  @Column(name = "consultora", nullable = false)
  private String consultora;

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
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitulo() {
    return this.titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getConsultora() {
    return this.consultora;
  }

  public void setConsultora(String consultora) {
    this.consultora = consultora;
  }

  public String getPeriodo() {
    return this.periodo;
  }

  public void setPeriodo(String periodo) {
    this.periodo = periodo;
  }

  public String getDescripcion() {
    return this.descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return this.updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Project() {
  }
}
