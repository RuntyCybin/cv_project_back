package com.cybindev.persona.domain;

import java.io.Serializable;
import java.time.LocalDate;

public record PersonaResponseDTO(
                Long id,
                String nombre,
                String apellidos,
                LocalDate fecha_nacimiento,
                String telefono,
                String email,
                String calle,
                String via,
                String numero_casa,
                String codigo_postal,
                String ciudad, String provincia, String pais, String nacionalidad) implements Serializable {

}
