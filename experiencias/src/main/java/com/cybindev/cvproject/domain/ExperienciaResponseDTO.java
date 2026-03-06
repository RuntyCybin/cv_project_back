package com.cybindev.cvproject.domain;

import java.io.Serializable;

public record ExperienciaResponseDTO(
                String puesto,
                String empresa,
                String descripcion,
                String fechaInicio,
                String fechaFin) implements Serializable {
}
