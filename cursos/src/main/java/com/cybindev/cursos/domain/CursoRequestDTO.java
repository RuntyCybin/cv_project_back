package com.cybindev.cursos.domain;

public record CursoRequestDTO(String nombre, String portal,
    String url, String autor, String descripcion, String periodo) {

}
