package com.cybindev.estudios.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cybindev.estudios.domain.EstudioRequestDTO;
import com.cybindev.estudios.domain.EstudioResponseDTO;
import com.cybindev.estudios.service.EstudioService;

@Service
public class EstudioServiceImpl implements EstudioService<EstudioResponseDTO, EstudioRequestDTO> {

  @Override
  public List<EstudioResponseDTO> listarEstudios() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'listarEstudios'");
  }

  @Override
  public EstudioResponseDTO crearEstudio(EstudioRequestDTO estudio) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'crearEstudio'");
  }

  @Override
  public EstudioResponseDTO obtenerEstudioPorId(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'obtenerEstudioPorId'");
  }

}
