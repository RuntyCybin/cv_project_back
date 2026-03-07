FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /workspace

COPY pom.xml pom.xml
COPY experiencias/pom.xml experiencias/pom.xml
COPY experiencias/src experiencias/src

RUN mvn -f experiencias/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=builder /workspace/experiencias/target/experiencias-*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
