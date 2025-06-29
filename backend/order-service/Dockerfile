FROM eclipse-temurin:21-jdk-alpine
LABEL authors="lorenipson"
COPY build/libs/order-service-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]