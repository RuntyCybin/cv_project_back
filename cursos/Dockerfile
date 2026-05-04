FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder
WORKDIR /workspace

COPY pom.xml pom.xml
COPY cursos/pom.xml cursos/pom.xml
COPY cursos/src cursos/src

RUN mvn -f cursos/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /workspace/cursos/target/cursos-*.jar app.jar

EXPOSE 8086
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
