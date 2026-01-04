# cv_project_back
Backend solution to the CV project

¿Porque docker-compose.yml esta fuera?
docker-compose.yml esta en la fuera del directorio del proyecto porque el docker-compose.yml suele orquestar servicios de todo el repo (p.ej. backend, futuras bases de datos, front), no solo la app de cvproject. Desde la raíz puede construir ./cvproject/Dockerfile y exponer puertos sin acoplarse a una sola subcarpeta, manteniendo la ruta de contexto limpia y permitiendo añadir otros servicios en el mismo compose más adelante.

En este docker compose no se va a agregar ningun serivcio mas. La parte front tiene su docker compose y la base de datos tendra el suyo.
Esta solucion viene dada porque es mas sencillo debuguear y probar cambios en el proyecto.