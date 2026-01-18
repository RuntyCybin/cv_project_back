package com.cybindev.cvproject;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExperienciaServiceImpl implements ExperienciaService {

  private final ExperienciaRepo experienciaRepo;
  private final ExperienciaRequestMapper experienciaRequestMapper;

  public ExperienciaServiceImpl(
      ExperienciaRepo experienciaRepo) {
    this.experienciaRepo = experienciaRepo;
    this.experienciaRequestMapper = new ExperienciaRequestMapperImpl();
  }

  @Override
  public ExperienciaResponseDTO getExperienciaById(Long id) {
    Optional<Experiencia> experiencia = experienciaRepo.findById(id);
    System.out.println("Fetching experiencia con id: " + experiencia.get().getId());

    Experiencia foundExp = Optional.ofNullable(experiencia)
        .map(exp -> experiencia.get())
        .orElseThrow(() -> new RuntimeException("Experiencia no puede ser nula."));

    ExperienciaResponseDTO experienciaDTO = new ExperienciaResponseDTO(
        foundExp.getPuesto(),
        foundExp.getEmpresa(),
        foundExp.getDescripcion(),
        "02-2022",
        "12-2023");

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
        .map(experiencia -> new ExperienciaResponseDTO(
            experiencia.getPuesto(),
            experiencia.getEmpresa(),
            experiencia.getDescripcion(),
            getFechas(experiencia.getPeriodo())[0],
            getFechas(experiencia.getPeriodo())[1]));
  }

  // periodo format: "MM-yyyy - MM-yyyy"
  private String[] getFechas(String periodo) {
    // lógica para extraer la fecha de inicio del periodo
    String[] partesPeriodo = periodo.split(" - ");

    String[] fechaInicio = partesPeriodo[0].split("-");
    String mesInicio = fechaInicio[0];
    String anioInicio = fechaInicio[1];

    String[] fechaFinal = partesPeriodo[1].split("-");
    String mesFin = fechaFinal[0];
    String anioFin = fechaFinal[1];

    if (Integer.parseInt(mesInicio) < Integer.parseInt(mesFin)
        && Integer.parseInt(anioInicio) <= Integer.parseInt(anioFin)) {
      return partesPeriodo;
    }

    // en caso de error, retornar una fecha por defecto
    return new String[] { "01-1970", "01-1970" };
  }
}
