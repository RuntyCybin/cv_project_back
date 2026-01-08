package com.cybindev.cvproject;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExperienciaServiceImpl implements ExperienciaService {

  private final ExperienciaRepo experienciaRepo;
  private final ExperienciaResponseMapper experienciaResponseMapper;
  private final ExperienciaRequestMapper experienciaRequestMapper;

  public ExperienciaServiceImpl(
      ExperienciaRepo experienciaRepo,
      ExperienciaResponseMapper experienciaMapper,
      ExperienciaRequestMapper experienciaRequestMapper) {
    this.experienciaRepo = experienciaRepo;
    this.experienciaResponseMapper = experienciaMapper;
    this.experienciaRequestMapper = experienciaRequestMapper;
  }

  @Override
  public ExperienciaResponseDTO getExperienciaById(Long id) {
    Optional<Experiencia> experiencia = experienciaRepo.findById(id);
    System.out.println("Fetching experiencia with id: " + experiencia.get().getId());

    Experiencia exp = new Experiencia();
    exp.setId(1L);
    exp.setPuesto("Desarrollador Java");
    exp.setEmpresa("Tech Solutions");
    exp.setPeriodo("Enero 2020 - Diciembre 2022");
    exp.setDescripcion("Desarrollo de aplicaciones empresariales utilizando Java y Spring Boot.");

    ExperienciaResponseDTO experienciaDTO = new ExperienciaResponseDTO(
        exp.getPuesto(),
        exp.getEmpresa(),
        exp.getDescripcion(),
        "02-2022",
        "12-2023");

    System.out.println("Returning experiencia DTO: " + experienciaDTO);
    return experienciaDTO;
    /*
     * return experiencia
     * .map(experienciaResponseMapper::convertExperienciaToDTO)
     * .orElseThrow(() -> new RuntimeException("Experiencia not found with id: " +
     * id));
     */
  }

  @Override
  public void addExperiencia(ExperienciaRequestDTO experienciaDTO) {
    Experiencia experiencia = new Experiencia();
    experiencia = experienciaRequestMapper.convertDTOToExperiencia(experienciaDTO);
    experienciaRepo.save(experiencia);
  }

  @Override
  public Page<ExperienciaResponseDTO> getExperienciaList(Pageable pageable) {
    return experienciaRepo.findAll(pageable).map(experienciaResponseMapper::convertExperienciaToDTO);
  }

}
