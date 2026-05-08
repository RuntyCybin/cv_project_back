package com.cybindev.project.service.impl;

import com.cybindev.project.domain.*;
import com.cybindev.project.repo.ProyectoRepo;
import com.cybindev.project.service.ProyectoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProyectoServiceImpl implements ProyectoService<ProyectoResponseDTO, ProyectoRequestDTO> {

  private final Logger logger = LoggerFactory.getLogger(ProyectoServiceImpl.class);
  private final ProyectoRepo repo;
  private final ProyectoRequestMapper requestMapper;
  private final ProyectResponseMapper responseMapper;

  public ProyectoServiceImpl(ProyectoRepo r,
      ProyectoRequestMapper requestMapper,
      ProyectResponseMapper responseMapper) {
    this.repo = r;
    this.requestMapper = requestMapper;
    this.responseMapper = responseMapper;
  }

  @Override
  public ProyectoResponseDTO crearProyecto(ProyectoRequestDTO requestDTO) {
    logger.info("Creando proyecto en la base de datos");

    return this.responseMapper.toDto(
        this.repo.save(
            Objects.requireNonNull(
                this.requestMapper.toEntity(
                    Objects.requireNonNull(requestDTO)))));
  }

  @Override
  public ProyectoResponseDTO obtenerProyectoPorId(Long id) {
    logger.info("Obteniendo proyecto con id: " + id);
    final Project result = this.repo.findById(Objects.requireNonNull(id))
        .orElseThrow(() -> new RuntimeException("El servicio ha fallado al recoger el proyecto"));
    return this.responseMapper.toDto(result);
  }

  @Override
  public List<ProyectoResponseDTO> listarProyectos() {
    final List<Project> result = this.repo.findAll();
    if (result.isEmpty()) {
      throw new RuntimeException("No se han recogido proyectos");
    }
    return result.stream()
        .map(this.responseMapper::toDto)
        .toList();
  }
}
