FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder
WORKDIR /workspace

COPY pom.xml pom.xml
COPY projects/pom.xml projects/pom.xml
COPY projects/src projects/src

RUN mvn -f projects/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /workspace/projects/target/projects-*.jar app.jar

EXPOSE 8087
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
