package com.cybindev.estudios.domain;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-01T18:56:18+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
public class EstudioResponseMapperImpl implements EstudioResponseMapper {

    @Override
    public EstudioResponseDTO toDto(Estudio entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String titulo = null;
        String institucion = null;
        String periodo = null;
        String descripcion = null;
        String cursos = null;

        id = entity.getId();
        titulo = entity.getTitulo();
        institucion = entity.getInstitucion();
        periodo = entity.getPeriodo();
        descripcion = entity.getDescripcion();
        cursos = entity.getCursos();

        EstudioResponseDTO estudioResponseDTO = new EstudioResponseDTO( id, titulo, institucion, periodo, descripcion, cursos );

        return estudioResponseDTO;
    }
}
