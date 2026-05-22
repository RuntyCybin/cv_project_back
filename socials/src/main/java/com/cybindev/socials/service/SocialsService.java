package com.cybindev.socials.service;

import java.util.List;

public interface SocialsService<O, I> {
  O crearRedSocial(I redSocial);

  O obtenerRedSocialPorId(Long id);

  List<O> listarRedesSociales();

}
