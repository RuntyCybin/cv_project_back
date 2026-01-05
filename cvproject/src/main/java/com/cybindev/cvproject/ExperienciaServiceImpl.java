package com.cybindev.cvproject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExperienciaServiceImpl implements ExperienciaService {

  private final ExperienciaRepo experienciaRepo;
  private final ExperienciaResponseMapper experienciaResponseMapper;

  public ExperienciaServiceImpl(ExperienciaRepo experienciaRepo, ExperienciaResponseMapper experienciaMapper) {
    this.experienciaRepo = experienciaRepo;
    this.experienciaResponseMapper = experienciaMapper;
  }

  @Override
  public ExperienciaResponseDTO getExperienciaById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getExperienciaById'");
  }

  @Override
  public void addExperiencia(ExperienciaResponseDTO experienciaDTO) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'addExperiencia'");
  }

  @Override
  public Page<ExperienciaResponseDTO> getExperienciaList(Pageable pageable) {
    return experienciaRepo.findAll(pageable).map(experienciaResponseMapper::convertExperienciaToDTO);
  }

}
