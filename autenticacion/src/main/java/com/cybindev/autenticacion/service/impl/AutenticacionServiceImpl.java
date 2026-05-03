package com.cybindev.autenticacion.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cybindev.autenticacion.domain.Autenticacion;
import com.cybindev.autenticacion.domain.AutenticacionRequestDTO;
import com.cybindev.autenticacion.domain.AutenticacionRequestMapper;
import com.cybindev.autenticacion.domain.AutenticacionResponseDTO;
import com.cybindev.autenticacion.domain.AutenticacionResponseMapper;
import com.cybindev.autenticacion.repo.AutenticacionRepo;
import com.cybindev.autenticacion.service.AutenticacionService;
import com.cybindev.autenticacion.service.JwtService;

@Service
public class AutenticacionServiceImpl
    implements AutenticacionService<AutenticacionResponseDTO, AutenticacionRequestDTO> {

  @Value("${app.title}")
  private String titleModulo;

  private static final Logger logger = LoggerFactory.getLogger(AutenticacionServiceImpl.class);
  private final AutenticacionRepo autenticacionRepo;
  private final AutenticacionRequestMapper autenticacionRequestMapper;
  private final AutenticacionResponseMapper autenticacionResponseMapper;
  private final JwtService jwtService;

  public AutenticacionServiceImpl(AutenticacionRepo autenticacionRepo,
      AutenticacionRequestMapper autenticacionRequestMapper,
      AutenticacionResponseMapper autenticacionResponseMapper,
      JwtService jwtServ) {
    this.autenticacionRepo = autenticacionRepo;
    this.autenticacionRequestMapper = autenticacionRequestMapper;
    this.autenticacionResponseMapper = autenticacionResponseMapper;
    this.jwtService = jwtServ;
  }

  /*
   * Crear autenticación
   */
  @Override
  public AutenticacionResponseDTO crearAutenticacion(AutenticacionRequestDTO autenticacionRequest) {
    logger.info("Creando autenticación para el usuario");

    if (autenticacionRequest != null) {
      Autenticacion autenticacion = autenticacionRequestMapper.toAutenticacion(autenticacionRequest);
      if (autenticacion != null) {
        // comprobar si ya existe este login
        boolean loginExiste = autenticacionRepo.findByLogin(autenticacionRequest.login()).isPresent();
        if (loginExiste) {
          logger.warn("{} - La Autenticacion con este login ya existe", titleModulo);
          throw new RuntimeException(titleModulo + " - Error al crear autenticación: login ya existe");
        }
        Autenticacion autenticacionGuardada = autenticacionRepo.save(autenticacion);
        return autenticacionResponseMapper.toResponse(autenticacionGuardada);
      } else {
        logger.warn("No se pudo mapear la solicitud de autenticación a una entidad Autenticacion");
        throw new RuntimeException("Error al crear autenticación: datos inválidos");
      }
    } else {
      logger.warn("No se proporcionaron login o password para una solicitud de autenticación válida");
      throw new RuntimeException("Login y password son requeridos para crear una autenticación");
    }
  }

  /*
   * Obtener autenticación por ID
   */
  @Override
  public AutenticacionResponseDTO obtenerAutenticacionPorId(Long id) {
    logger.info("Obteniendo autenticación por ID");

    if (id > 0L) {
      Autenticacion autenticacionEncontrada = autenticacionRepo.findById(id)
          .orElseThrow(() -> new RuntimeException("Autenticación no encontrada"));
      return autenticacionResponseMapper.toResponse(autenticacionEncontrada);
    } else {
      logger.warn("ID de autenticación no válido proporcionado para la búsqueda: {}", id);
      throw new RuntimeException("ID de autenticación es requerido para buscar una autenticación por ID");
    }
  }

  /*
   * Obtener autenticación por login
   */
  @Override
  public AutenticacionResponseDTO obtenerAutenticacionPorLogin(String login) {
    logger.info("Obteniendo el login por login nada mas");

    boolean isEmpty = login == null || login.isEmpty();
    if (!isEmpty) {
      Autenticacion autenticacionEncontrada = autenticacionRepo.findByLogin(login)
          .orElseThrow(() -> new RuntimeException("Login no encontrado"));
      return autenticacionResponseMapper.toResponse(autenticacionEncontrada);
    } else {
      logger.warn("No se proporcionó un login válido para la búsqueda de autenticación");
      throw new RuntimeException("Login es requerido para buscar una autenticación por login");
    }
  }

  /*
   * Servicio para lguearse con login y password, se valida que ambos campos no
   * sean vacios,
   * luego se busca en la base de datos una autenticacion que coincida con el
   * login y password proporcionados,
   * si se encuentra se retorna la respuesta con los datos de la autenticacion
   * encontrada,
   * si no se encuentra se lanza una excepcion indicando que el login o password
   * son incorrectos.
   */
  @Override
  @Transactional(readOnly = true)
  public AutenticacionResponseDTO obtenerAutenticacionPorLoginYPassword(
      AutenticacionRequestDTO autenticacionRequest) {
    logger.info("Realizando el login con login y password");

    if (autenticacionRequest != null) {
      boolean isEmptyLogin = autenticacionRequest.login() == null || autenticacionRequest.login().isEmpty();
      boolean isEmptyPassword = autenticacionRequest.password() == null || autenticacionRequest.password().isEmpty();
      if (isEmptyPassword || isEmptyLogin) {
        throw new RuntimeException("Login y password son requeridos");
      }

      Autenticacion autenticacion = autenticacionRequestMapper.toAutenticacion(autenticacionRequest);

      Autenticacion autenticacionEncontrada = autenticacionRepo.findByLoginAndPassword(
          autenticacion.getLogin(),
          autenticacion.getPassword())
          .orElseThrow(() -> new RuntimeException("Login o password incorrectos"));

      if (autenticacionEncontrada != null) {
        logger.info("Login exitoso para el usuario: {}", autenticacionEncontrada.getLogin());

        // generamos el token
        String jwt = jwtService.generarToken(autenticacionEncontrada.getLogin(), autenticacionEncontrada.getId());
        boolean isEmptyJwt = jwt == null || jwt.isEmpty();

        if (!isEmptyJwt) {
          return autenticacionResponseMapper.toResponseConJwt(autenticacionEncontrada, jwt);
        } else {
          logger.warn("Error al generar el Token JWT");
          throw new RuntimeException("Error al generar el Token JWT");
        }
      } else {
        logger.warn("Login o password incorrectos para el usuario: {}", autenticacionRequest.login());
        throw new RuntimeException("Login o password incorrectos");
      }
    } else {
      logger.warn("No se proporcionaron login o password para una solicitud de autenticación válida");
      throw new RuntimeException("Login y password son requeridos para realizar el login");
    }
  }

}
