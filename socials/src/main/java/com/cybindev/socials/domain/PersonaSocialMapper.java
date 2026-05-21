package com.cybindev.socials.domain;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class PersonaSocialMapper {

  @NonNull
  public PersonaSocialResponseDTO toResponseDTO(PersonaSocial personaSocial) {
    return new PersonaSocialResponseDTO(
        personaSocial.getId(),
        personaSocial.getPersonaId(),
        personaSocial.getSocialId());
  }

  @NonNull
  public PersonaSocial toEntity(PersonaSocialRequestDTO requestDTO) {
    PersonaSocial personaSocial = new PersonaSocial();
    personaSocial.setPersonaId(requestDTO.personaId());
    personaSocial.setSocialId(requestDTO.socialId());
    return personaSocial;
  }
}
