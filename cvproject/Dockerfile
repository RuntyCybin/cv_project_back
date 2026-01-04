FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copiamos el descriptor y descargamos dependencias sin código fuente para cachear capas.
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Añadimos el código y generamos el artefacto.
COPY src ./src
RUN mvn -B package -DskipTests

# Imagen final ligera solo con el runtime de Java.
FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
