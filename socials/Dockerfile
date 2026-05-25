FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder
WORKDIR /workspace

COPY pom.xml pom.xml
COPY socials/pom.xml socials/pom.xml
COPY socials/src socials/src

RUN mvn -f socials/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /workspace/socials/target/socials-*.jar app.jar

EXPOSE 8089
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
