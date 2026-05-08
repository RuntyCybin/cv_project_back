package com.cybindev.experiencia.domain;

import java.io.Serializable;

public record ExperienciaResponseDTO(
        Long id,
        String puesto,
        String empresa,
        String descripcion,
        String fechaInicio,
        String fechaFin) implements Serializable {
}
