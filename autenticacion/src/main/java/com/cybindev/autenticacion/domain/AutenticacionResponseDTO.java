package com.cybindev.autenticacion.domain;

public record AutenticacionResponseDTO(Long id, String login, String password, String jwt) {

}
