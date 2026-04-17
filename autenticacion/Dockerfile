FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /workspace

COPY pom.xml pom.xml
COPY autenticacion/pom.xml autenticacion/pom.xml
COPY autenticacion/src autenticacion/src

RUN mvn -f autenticacion/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /workspace/autenticacion/target/autenticacion-*.jar app.jar

EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
