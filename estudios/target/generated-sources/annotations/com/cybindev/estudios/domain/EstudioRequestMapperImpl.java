package com.cybindev.estudios.domain;

import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-01T18:56:18+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
public class EstudioRequestMapperImpl implements EstudioRequestMapper {

    @Override
    public Estudio toEntity(EstudioRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Estudio estudio = new Estudio();

        estudio.setTitulo( dto.titulo() );
        estudio.setInstitucion( dto.institucion() );
        estudio.setPeriodo( dto.periodo() );
        estudio.setDescripcion( dto.descripcion() );
        estudio.setCursos( dto.cursos() );

        return estudio;
    }
}
