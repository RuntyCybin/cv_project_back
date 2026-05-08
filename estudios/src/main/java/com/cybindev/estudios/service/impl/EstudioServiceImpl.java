package com.cybindev.estudios.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cybindev.estudios.domain.Estudio;
import com.cybindev.estudios.domain.EstudioRequestDTO;
import com.cybindev.estudios.domain.EstudioRequestMapper;
import com.cybindev.estudios.domain.EstudioResponseDTO;
import com.cybindev.estudios.domain.EstudioResponseMapper;
import com.cybindev.estudios.repo.EstudiosRepo;
import com.cybindev.estudios.service.EstudioService;

@Service
public class EstudioServiceImpl implements EstudioService<EstudioResponseDTO, EstudioRequestDTO> {

  private final Logger logger = LoggerFactory.getLogger(EstudioServiceImpl.class);
  private final EstudiosRepo repo;
  private final EstudioRequestMapper mapperRequest;
  private final EstudioResponseMapper mapperResponse;

  EstudioServiceImpl(EstudiosRepo repo, EstudioRequestMapper mapperRequest,
      EstudioResponseMapper mapperResponse) {
    this.repo = repo;
    this.mapperRequest = mapperRequest;
    this.mapperResponse = mapperResponse;
  }

  /*
   * Listar todos los estudios
   */
  @Override
  public List<EstudioResponseDTO> listarEstudios() {
    logger.info("Listing all estudios from the database");

    List<Estudio> estudios = repo.findAll();
    if (estudios.isEmpty()) {
      throw new RuntimeException("No se han recogido estudios");
    }
    return estudios.stream()
        .map(estudio -> this.mapperResponse.toDto(Objects.requireNonNull(estudio)))
        .toList();
  }

  /*
   * Create Estudio
   */
  @Override
  public EstudioResponseDTO crearEstudio(EstudioRequestDTO estudioDto) {
    logger.info("Creating a new estudio in the database");
    return this.mapperResponse.toDto(
        repo.save(
            Objects.requireNonNull(this.mapperRequest.toEntity(estudioDto))));
  }

  /*
   * Get Estudio by ID
   */
  @Override
  public EstudioResponseDTO obtenerEstudioPorId(Long id) {
    logger.info("Obteniendo estudio con id: " + id);

    return this.mapperResponse.toDto(
        repo.findById(Objects.requireNonNull(id))
            .orElseThrow(() -> new RuntimeException("Estudio not found with id: " + id)));
  }

}
