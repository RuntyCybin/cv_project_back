FROM maven:3.9.9-eclipse-temurin-21-alpine AS builder
WORKDIR /workspace

COPY pom.xml pom.xml
COPY estudios/pom.xml estudios/pom.xml
COPY estudios/src estudios/src

RUN mvn -f estudios/pom.xml clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /workspace/estudios/target/estudios-*.jar app.jar

EXPOSE 8085
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
