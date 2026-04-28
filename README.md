# cv_project_back
Backend solution to the CV project


## ¿Porque docker-compose.yml esta fuera?

docker-compose.yml esta en la fuera del directorio del proyecto porque el docker-compose.yml suele orquestar servicios de todo el repo (p.ej. backend, futuras bases de datos, front), no solo la app de experiencias. Desde la raíz puede construir ./experiencias/Dockerfile y exponer puertos sin acoplarse a una sola subcarpeta, manteniendo la ruta de contexto limpia y permitiendo añadir otros servicios en el mismo compose más adelante.

En este docker compose no se va a agregar ningun serivcio mas. La parte front tiene su docker compose y la base de datos tendra el suyo.
Esta solucion viene dada porque es mas sencillo debuguear y probar cambios en el proyecto.

### Added container setup to run the Spring Boot app in Docker.

Added `experiencias/Dockerfile` with a Maven build stage (Java 21) and slim runtime image.
Created `docker-compose.yml` to build from `experiencias`, expose `8080`, set default Spring profile, and auto-restart.


## Variables de entorno

To run this project, you will need to add the following environment variables to your .env file

`API_KEY`

`ANOTHER_API_KEY`


## Arrancar proyecto para prueba de nuevos cambios en desarrollo

Sin docker!
Desde la raiz de README.MD

```bash
  cd experiencias
  mvn clean install

```




## Instalacion del proyecto

Arrancar proyect con Docker

```bash
  npm install my-project
  cd my-project
```

## Puertos

Guia de puertos del proyecto

```bash
  8080 : API-Gateway
  8081 : Front
  8082 : API datos de persona
  8083 : API autenticacion
  8084 : API experiencias
  8085 : API estudios
  8086 : API cursos 
  8087 : API projects
```



## Authors

- [@RuntyCybin](https://github.com/RuntyCybin)
