package com.cybindev.estudios.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cybindev.estudios.domain.Estudio;
import com.cybindev.estudios.domain.EstudioRequestDTO;
import com.cybindev.estudios.domain.EstudioRequestMapper;
import com.cybindev.estudios.domain.EstudioRequestMapperImpl;
import com.cybindev.estudios.domain.EstudioResponseDTO;
import com.cybindev.estudios.domain.EstudioResponseMapper;
import com.cybindev.estudios.domain.EstudioResponseMapperImpl;
import com.cybindev.estudios.repo.EstudiosRepo;
import com.cybindev.estudios.service.EstudioService;

@Service
public class EstudioServiceImpl implements EstudioService<EstudioResponseDTO, EstudioRequestDTO> {

  private final EstudiosRepo repo;
  private final EstudioRequestMapper mapperRequest;
  private final EstudioResponseMapper mapperResponse;

  EstudioServiceImpl(EstudiosRepo repo) {
    this.repo = repo;
    this.mapperRequest = new EstudioRequestMapperImpl();
    this.mapperResponse = new EstudioResponseMapperImpl();
  }

  /*
   * Listar todos los estudios
   */
  @Override
  public List<EstudioResponseDTO> listarEstudios() {
    System.out.println("Listing all estudios from the database");
    List<Estudio> estudios = repo.findAll();
    System.out.println("Found " + estudios.size() + " estudios in the database");
    if (estudios.size() == 0) {
      throw new RuntimeException("No se han recogido estudios");
    }
    return estudios.stream()
        .map(estudio -> this.mapperResponse.toDto(estudio))
        .toList();
  }

  /*
   * Create Estudio
   */
  @Override
  public EstudioResponseDTO crearEstudio(EstudioRequestDTO estudioDto) {
    System.out.println("Creating a new estudio in the database");
    Estudio nuevoEstudio = this.mapperRequest.toEntity(estudioDto);
    Estudio guardado = repo.save(nuevoEstudio);
    if (guardado == null) {
      throw new RuntimeException("Service Failed to save Estudio");
    }

    return this.mapperResponse.toDto(guardado);
  }

  /*
   * Get Estudio by ID
   */
  @Override
  public EstudioResponseDTO obtenerEstudioPorId(Long id) {
    System.out.println("Obteniendo estudio con id: " + id);
    Estudio estudio = repo.findById(id)
        .orElseThrow(() -> new RuntimeException("Estudio not found with id: " + id));

    if (estudio == null) {
      throw new RuntimeException("Service failure to obtain an Estudio");
    }
    return this.mapperResponse.toDto(estudio);
  }

}
