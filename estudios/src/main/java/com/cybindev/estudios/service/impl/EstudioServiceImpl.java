package com.cybindev.estudios.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.cybindev.estudios.domain.Estudio;
import com.cybindev.estudios.domain.EstudioRequestDTO;
import com.cybindev.estudios.domain.EstudioResponseDTO;
import com.cybindev.estudios.repo.EstudiosRepo;
import com.cybindev.estudios.service.EstudioService;

@Service
public class EstudioServiceImpl implements EstudioService<EstudioResponseDTO, EstudioRequestDTO> {

  private final EstudiosRepo repo;

  EstudioServiceImpl(EstudiosRepo repo) {
    this.repo = repo;
  }

  /*
   * All Estudios Listing
   */
  @Override
  public List<EstudioResponseDTO> listarEstudios() {
    System.out.println("Listing all estudios from the database");
    List<Estudio> estudios = repo.findAll();
    System.out.println("Found " + estudios.size() + " estudios in the database");
    if (estudios.size() == 0) {
      throw new RuntimeException("Service failure for listing estudios");
    }

    return estudios.stream()
        .map(estudio -> new EstudioResponseDTO(
            estudio.getId(),
            estudio.getTitulo(),
            estudio.getInstitucion(),
            estudio.getPeriodo(),
            estudio.getDescripcion(),
            estudio.getCursos()))
        .toList();
  }

  /*
   * Create Estudio
   */
  @Override
  public EstudioResponseDTO crearEstudio(EstudioRequestDTO estudioDto) {
    System.out.println("Creating a new estudio in the database");
    Estudio nuevoEstudio = new Estudio();

    nuevoEstudio.setTitulo(estudioDto.titulo());
    nuevoEstudio.setInstitucion(estudioDto.institucion());
    nuevoEstudio.setPeriodo(estudioDto.periodo());
    nuevoEstudio.setDescripcion(estudioDto.descripcion());
    nuevoEstudio.setCursos(estudioDto.cursos());

    System.out.println(":::::::::nuevoEstudio: " + nuevoEstudio.getCreatedAt());
    System.out.println(":::::::::nuevoEstudio: " + nuevoEstudio.getUpdatedAt());
    System.out.println(":::::::::nuevoEstudio: " + nuevoEstudio.getCursos());

    Estudio guardado = repo.save(nuevoEstudio);
    if (guardado == null) {
      throw new RuntimeException("Service Failed to save Estudio");
    }
    return new EstudioResponseDTO(
        guardado.getId(),
        guardado.getTitulo(),
        guardado.getInstitucion(),
        guardado.getPeriodo(),
        guardado.getDescripcion(),
        guardado.getCursos());
  }

  /*
   * Get Estudio by ID
   */
  @Override
  public EstudioResponseDTO obtenerEstudioPorId(Long id) {
    Estudio estudio = repo.findById(id)
        .orElseThrow(() -> new RuntimeException("Estudio not found with id: " + id));

    if (estudio == null) {
      throw new RuntimeException("Service failure to obtain an Estudio");
    }
    return new EstudioResponseDTO(
        estudio.getId(),
        estudio.getTitulo(),
        estudio.getInstitucion(),
        estudio.getPeriodo(),
        estudio.getDescripcion(),
        estudio.getCursos());
  }

}
