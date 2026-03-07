package com.cybindev.service.impl;

import com.cybindev.domain.*;
import com.cybindev.repo.ProyectoRepo;
import com.cybindev.service.ProyectoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProyectoServiceImpl implements ProyectoService<ProyectoResponseDTO, ProyectoRequestDTO> {

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
    System.out.println("Creando un nuevo proyecto en la base de datos");
    final Project nuevoProyecto = this.requestMapper.toEntity(requestDTO);
    final Project guardado = this.repo.save(nuevoProyecto);
    if (guardado == null) {
      throw new RuntimeException("El servicio ha fallado al guardar el proyecto");
    }

    return this.responseMapper.toDto(guardado);
  }

  @Override
  public ProyectoResponseDTO obtenerProyectoPorId(Long id) {
    final Project result = this.repo.findById(id)
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
