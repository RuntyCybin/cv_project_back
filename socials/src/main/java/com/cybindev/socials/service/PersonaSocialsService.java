package com.cybindev.socials.service;

import java.util.List;

import com.cybindev.socials.domain.SocialResponseDTO;

public interface PersonaSocialsService<O, I> {
  O crearPersonaRedSocial(I personaRedSocial);

  O obtenerPersonaRedSocialPorId(Long id);

  List<SocialResponseDTO> obtenerRedesSocialesPorPersonaId(Long personaId);
}
