package com.cybindev.cvproject;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExperienciaServiceImpl implements ExperienciaService {

  @Value("${app.title}")
  private String title;

  private final ExperienciaRepo experienciaRepo;
  private final ExperienciaRequestMapper experienciaRequestMapper;
  private final ExperienciaResponseMapper experienciaResponseMapper;

  public ExperienciaServiceImpl(
      ExperienciaRepo experienciaRepo) {
    this.experienciaRepo = experienciaRepo;
    this.experienciaRequestMapper = new ExperienciaRequestMapperImpl();
    this.experienciaResponseMapper = new ExperienciaResponseMapperImpl();
  }

  @Override
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
  public Experiencia addExperiencia(ExperienciaRequestDTO experienciaDTO) {

    Experiencia experiencia = experienciaRequestMapper.toEntity(experienciaDTO);

    Experiencia experienciaResultSave = experienciaRepo.save(experiencia);
    System.out.println("Saved experiencia: " + experienciaResultSave);

    return Optional
        .of(experienciaResultSave)
        .orElseThrow(() -> new RuntimeException("Error saving experiencia"));
  }

  @Override
  public Page<ExperienciaResponseDTO> getExperienciaList(Pageable pageable) {
    return experienciaRepo.findAll(pageable)
        .map(experiencia -> experienciaResponseMapper.toDto(experiencia));
  }
}
