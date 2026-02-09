package com.cybindev.controller;

import com.cybindev.domain.ProyectoRequestDTO;
import com.cybindev.domain.ProyectoResponseDTO;
import com.cybindev.service.ProyectoService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {

  private final ProyectoService<ProyectoResponseDTO, ProyectoRequestDTO> service;

  public ProjectController(ProyectoService<ProyectoResponseDTO, ProyectoRequestDTO> s) {
    this.service = s;
  }

  @PostConstruct
  public void init() {
    System.out.println("Proyectos Controller initialized");
  }

  /*
   * ------------------------------------------
   * HEALTH CHECK
   * ------------------------------------------
   */
  @GetMapping
  public ResponseEntity<String> health() {
    return ResponseEntity.status(HttpStatus.OK)
            .body("Proyectos controller is healthy");
  }
  /*
   * ------------------------------------------
   * !HEALTH CHECK
   * ------------------------------------------
   */


  /*
   * ------------------------------------------
   * GET ALL PROYECTOS
   * ------------------------------------------
   */
  @GetMapping("/getProyectos")
  @CircuitBreaker(name = "getAllProyectosService", fallbackMethod = "fallBackGetAllProyectos")
  public ResponseEntity<List<ProyectoResponseDTO>> getProyectos() {
    return ResponseEntity.status(HttpStatus.OK)
            .body(this.service.listarProyectos());
  }

  public ResponseEntity<List<ProyectoResponseDTO>> fallBackGetAllProyectos(Throwable throwable) {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(List.of(new ProyectoResponseDTO(
                    -1L,
                    "Fallback GetAll Proyectos",
                    "N/A",
                    "No se han podido obtener los proyectos",
                    "N/A")));
  }
  /*
   * ------------------------------------------
   * !GET ALL PROYECTOS
   * ------------------------------------------
   */

  /*
   * ------------------------------------------
   * POST PROYECTOS
   * ------------------------------------------
   */
  @PostMapping("/postProyecto")
  @CircuitBreaker(name = "createProyctoService", fallbackMethod = "fallBackCreateProyecto")
  public ResponseEntity<ProyectoResponseDTO> postProyecto(@RequestBody ProyectoRequestDTO request) {
    final ProyectoResponseDTO response = this.service.crearProyecto(request);
    return ResponseEntity.status(HttpStatus.OK)
            .body(response);
  }

  public ResponseEntity<ProyectoResponseDTO> fallBackCreateProyecto(@RequestBody ProyectoRequestDTO request, Throwable throwable) {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(new ProyectoResponseDTO(
                    -1L,
                    "Fallback Create Proyecto",
                    "N/A",
                    "No se han podido crear el proyecto",
                    "N/A"));
  }
  /*
   * ------------------------------------------
   * !POST PROYECTOS
   * ------------------------------------------
   */

  /*
   * ------------------------------------------
   * GET PROYECTO POR ID
   * ------------------------------------------
   */
  @GetMapping("/getProyecto/{id}")
  @CircuitBreaker(name = "getProyectoPorId", fallbackMethod = "fallBackGetProyectoPorId")
  public ResponseEntity<ProyectoResponseDTO> getCursoPorId(@PathVariable Long id) {
    final ProyectoResponseDTO response = this.service.obtenerProyectoPorId(id);
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(response);
  }

  public ResponseEntity<ProyectoResponseDTO> fallBackGetProyectoPorId(@PathVariable Long id, Throwable throwable) {
    return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(new ProyectoResponseDTO(
                    -1L,
                    "Fallback Get Proyecto",
                    "N/A",
                    "No se ha podido obtener el proyecto por su id",
                    "N/A"));
  }
  /*
   * ------------------------------------------
   * !GET PROYECTO POR ID
   * ------------------------------------------
   */
}
