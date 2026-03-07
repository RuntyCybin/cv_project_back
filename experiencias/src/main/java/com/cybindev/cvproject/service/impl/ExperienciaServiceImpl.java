package com.cybindev.cvproject.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cybindev.cvproject.domain.Experiencia;
import com.cybindev.cvproject.domain.ExperienciaRequestDTO;
import com.cybindev.cvproject.domain.ExperienciaRequestMapper;
import com.cybindev.cvproject.domain.ExperienciaResponseDTO;
import com.cybindev.cvproject.domain.ExperienciaResponseMapper;
import com.cybindev.cvproject.repository.ExperienciaRepo;
import com.cybindev.cvproject.service.ExperienciaService;

@Service
public class ExperienciaServiceImpl implements ExperienciaService<ExperienciaResponseDTO, ExperienciaRequestDTO> {

  @Value("${app.title}")
  private String title;

  private final ExperienciaRepo experienciaRepo;
  private final ExperienciaRequestMapper experienciaRequestMapper;
  private final ExperienciaResponseMapper experienciaResponseMapper;

  public ExperienciaServiceImpl(ExperienciaRepo experienciaRepo,
      ExperienciaResponseMapper experienciaResponseMapper, ExperienciaRequestMapper experienciaRequestMapper) {
    this.experienciaRepo = experienciaRepo;
    this.experienciaRequestMapper = experienciaRequestMapper;
    this.experienciaResponseMapper = experienciaResponseMapper;
  }

  @Override
  @Cacheable(value = "experienciaCache", key = "#id")
  public ExperienciaResponseDTO getExperienciaById(Long id) {
    System.out.println("Proyecto " + title + ": Getting experiencia by id: " + id);

    Optional<Experiencia> experiencia = experienciaRepo.findById(id);
    System.out.println("Fetching experiencia con id: " + experiencia.get().getId());

    Experiencia foundExp = Optional.ofNullable(experiencia)
        .map(exp -> experiencia.get())
        .orElseThrow(() -> new RuntimeException("Experiencia no puede ser nula."));

    ExperienciaResponseDTO experienciaDTO = experienciaResponseMapper.toDto(foundExp);

    System.out.println("Returning mock de experiencia DTO: " + experienciaDTO);
    return experienciaDTO;
  }

  @Override
  @CachePut(value = "experienciaCache", key = "#result.id")
  public ExperienciaResponseDTO addExperiencia(ExperienciaRequestDTO experienciaDTO) {

    Experiencia experiencia = experienciaRequestMapper.toEntity(experienciaDTO);

    Experiencia experienciaResultSave = experienciaRepo.save(experiencia);
    ExperienciaResponseDTO experienciaResponseDTO = experienciaResponseMapper.toDto(experienciaResultSave);
    System.out.println("Saved experiencia: " + experienciaResultSave);

    return Optional.of(experienciaResponseDTO)
        .orElseThrow(() -> new RuntimeException("Error saving experiencia"));
  }

  @Override
  public Page<ExperienciaResponseDTO> getExperienciaList(Pageable pageable) {
    return experienciaRepo.findAll(pageable)
        .map(experiencia -> experienciaResponseMapper.toDto(experiencia));
  }
}
