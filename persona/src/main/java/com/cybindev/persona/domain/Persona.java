package com.cybindev.persona.domain;

import java.time.LocalDate;
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
@Table(name = "persona")
public class Persona {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @Column(name = "apellidos", nullable = false)
  private String apellidos;

  @Column(name = "fecha_nacimiento", nullable = false)
  private LocalDate fecha_nacimiento;

  @Column(name = "telefono")
  private String telefono;

  @Column(name = "email", unique = true)
  private String email;

  @Column(name = "calle")
  private String calle;

  @Column(name = "via")
  private String via;

  @Column(name = "numero_casa")
  private String numero_casa;

  @Column(name = "codigo_postal")
  private String codigo_postal;

  @Column(name = "ciudad")
  private String ciudad;

  @Column(name = "provincia")
  private String provincia;

  @Column(name = "pais")
  private String pais;

  @Column(name = "nacionalidad")
  private String nacionalidad;

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

  // getters y setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getApellidos() {
    return apellidos;
  }

  public void setApellidos(String apellidos) {
    this.apellidos = apellidos;
  }

  public LocalDate getFecha_nacimiento() {
    return fecha_nacimiento;
  }

  public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
    this.fecha_nacimiento = fecha_nacimiento;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getCalle() {
    return calle;
  }

  public void setCalle(String calle) {
    this.calle = calle;
  }

  public String getVia() {
    return via;
  }

  public void setVia(String via) {
    this.via = via;
  }

  public String getNumero_casa() {
    return numero_casa;
  }

  public void setNumero_casa(String numero_casa) {
    this.numero_casa = numero_casa;
  }

  public String getCodigo_postal() {
    return codigo_postal;
  }

  public void setCodigo_postal(String codigo_postal) {
    this.codigo_postal = codigo_postal;
  }

  public String getCiudad() {
    return ciudad;
  }

  public void setCiudad(String ciudad) {
    this.ciudad = ciudad;
  }

  public String getProvincia() {
    return provincia;
  }

  public void setProvincia(String provincia) {
    this.provincia = provincia;
  }

  public String getPais() {
    return pais;
  }

  public void setPais(String pais) {
    this.pais = pais;
  }

  public String getNacionalidad() {
    return nacionalidad;
  }

  public void setNacionalidad(String nacionalidad) {
    this.nacionalidad = nacionalidad;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Persona() {
  }

}
