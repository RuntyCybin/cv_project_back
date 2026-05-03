package com.cybindev.cvproject.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
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

  private static final Logger logger = LoggerFactory.getLogger(ExperienciaServiceImpl.class);
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
    logger.info("Proyecto " + title + ": Getting experiencia by id: " + id);

    if (id > 0L) {
      Experiencia foundExp = experienciaRepo.findById(id)
          .orElseThrow(() -> new RuntimeException("Experiencia no encontrada con id: " + id));

      ExperienciaResponseDTO experienciaDTO = experienciaResponseMapper.toDto(foundExp);

      logger.info("Returning experiencia DTO: " + experienciaDTO);
      return experienciaDTO;
    } else {
      logger.error("ERROR: id de experiencia ha de ser un numero entero");
      throw new RuntimeException("ERROR: id de experiencia ha de ser un numero entero");
    }
  }

  @Override
  @CachePut(value = "experienciaCache", key = "#result.id")
  public ExperienciaResponseDTO addExperiencia(ExperienciaRequestDTO experienciaDTO) {

    if (null != experienciaDTO) {

      // TODO: validar que el la experiencia no exista ya en la base de datos para
      // este usuario
      Optional<Experiencia> existingExperiencia = experienciaRepo.findByPuestoAndEmpresa(experienciaDTO.puesto(),
          experienciaDTO.empresa());

      if (existingExperiencia.isPresent()) {
        logger.warn("Experiencia con puesto '{}' y empresa '{}' ya existe", experienciaDTO.puesto(),
            experienciaDTO.empresa());
        throw new RuntimeException("Experiencia con puesto " + experienciaDTO.puesto() + " y empresa "
            + experienciaDTO.empresa() + " ya existe");
      }

      Experiencia experiencia = experienciaRequestMapper.toEntity(experienciaDTO);

      if (null != experiencia) {
        Experiencia experienciaResultSave = experienciaRepo.save(experiencia);
        ExperienciaResponseDTO experienciaResponseDTO = experienciaResponseMapper.toDto(experienciaResultSave);
        logger.info("Saved experiencia: " + experienciaResultSave);

        return experienciaResponseDTO;
      } else {
        logger.error("ERROR: mapeando dto a entidad");
        throw new RuntimeException("ERROR: mapeando dto a entidad");
      }
    } else {
      logger.error("ERROR: request dto es nula");
      throw new RuntimeException("ERROR: request dto es nula");
    }
  }

  @Override
  public Page<ExperienciaResponseDTO> getExperienciaList(Pageable pageable) {
    logger.info("Servicio para listar todas las experiencias con paginacion");
    if (null != pageable) {
      return experienciaRepo.findAll(pageable)
          .map(experiencia -> experienciaResponseMapper.toDto(experiencia));
    } else {
      logger.error("ERROR: objeto pageable es nulo");
      throw new RuntimeException("ERROR: objeto pageable es nulo");
    }
  }

  @Override
  @CacheEvict(value = "experienciaCache", allEntries = true)
  public void eliminarExperiencia(ExperienciaRequestDTO experienciaDto) {
    logger.info("Servicio para eliminar experiencia");
    if (null != experienciaDto) {
      Experiencia experiencia = experienciaRequestMapper.toEntity(experienciaDto);
      if (null != experiencia) {
        experienciaRepo.delete(experiencia);
      } else {
        logger.error("ERROR: el mapeo del DTO a entidad fallo");
        throw new RuntimeException("ERROR: el mapeo del DTO a entidad fallo");
      }
    } else {
      logger.error("ERROR: el dto de experiencia es nulo");
      throw new RuntimeException("ERROR: el dto de experiencia es nulo");
    }
  }
}
