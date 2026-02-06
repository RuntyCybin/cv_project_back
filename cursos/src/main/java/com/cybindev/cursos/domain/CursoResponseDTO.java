package com.cybindev.cursos.domain;

public record CursoResponseDTO(Long id, String nombre, String portal,
    String url, String autor, String descripcion, String periodo) {

}
