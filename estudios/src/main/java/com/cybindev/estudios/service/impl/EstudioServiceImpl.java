package com.cybindev.estudios.service.impl;

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

  @Override
  public List<EstudioResponseDTO> listarEstudios() {
    System.out.println("Listing all estudios from the database");
    List<Estudio> estudios = repo.findAll();
    System.out.println("Found " + estudios.size() + " estudios in the database");
    if (estudios.size() == 0) {
      throw new RuntimeException("Service failure for testing fallback");
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

  @Override
  public EstudioResponseDTO crearEstudio(EstudioRequestDTO estudio) {
    Estudio nuevoEstudio = new Estudio(
        null,
        estudio.titulo(),
        estudio.institucion(),
        estudio.periodo(),
        estudio.descripcion(),
        estudio.cursos(),
        null,
        null);
    Estudio guardado = repo.save(nuevoEstudio);
    return new EstudioResponseDTO(
        guardado.getId(),
        guardado.getTitulo(),
        guardado.getInstitucion(),
        guardado.getPeriodo(),
        guardado.getDescripcion(),
        guardado.getCursos());
  }

  @Override
  public EstudioResponseDTO obtenerEstudioPorId(Long id) {
    Estudio estudio = repo.findById(id)
        .orElseThrow(() -> new RuntimeException("Estudio not found with id: " + id));
    return new EstudioResponseDTO(
        estudio.getId(),
        estudio.getTitulo(),
        estudio.getInstitucion(),
        estudio.getPeriodo(),
        estudio.getDescripcion(),
        estudio.getCursos());
  }

}
