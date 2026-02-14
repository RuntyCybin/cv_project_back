package com.cybindev.persona.domain;

public record PersonaRequestDTO(String nombre, String apellidos, String fecha_nacimiento,
    String telefono, String email, String calle, String via, String numero_casa,
    String codigo_postal, String ciudad, String provincia, String pais, String nacionalidad) {

}
