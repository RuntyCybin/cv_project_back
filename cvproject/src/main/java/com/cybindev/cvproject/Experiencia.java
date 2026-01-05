package com.cybindev.cvproject;

import java.sql.Time;

public class Experiencia {

  private Long id;
  private String puesto;
  private String empresa;
  private String periodo;
  private String descripcion;
  private Time createdAt;
  private Time updatedAt;

  public Experiencia() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPuesto() {
    return puesto;
  }

  public void setPuesto(String puesto) {
    this.puesto = puesto;
  }

  public String getEmpresa() {
    return empresa;
  }

  public void setEmpresa(String empresa) {
    this.empresa = empresa;
  }

  public String getPeriodo() {
    return periodo;
  }

  public void setPeriodo(String periodo) {
    this.periodo = periodo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Time getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Time createdAt) {
    this.createdAt = createdAt;
  }

  public Time getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Time updatedAt) {
    this.updatedAt = updatedAt;
  }

}
