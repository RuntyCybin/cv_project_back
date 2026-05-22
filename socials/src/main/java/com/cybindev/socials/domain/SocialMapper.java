package com.cybindev.socials.domain;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class SocialMapper {

  @NonNull
  public SocialResponseDTO toResponseDTO(Social social) {
    return new SocialResponseDTO(
        social.getId(),
        social.getLinkedIn(),
        social.getGithub(),
        social.getWhatsapp(),
        social.getTelegram());
  }

  @NonNull
  public Social toEntity(SocialRequestDTO requestDTO) {
    Social social = new Social();
    social.setLinkedIn(requestDTO.linkedIn());
    social.setGithub(requestDTO.github());
    social.setWhatsapp(requestDTO.whatsapp());
    social.setTelegram(requestDTO.telegram());
    return social;
  }
}
