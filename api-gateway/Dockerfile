FROM openjdk:17-jdk-slim
ARG JAR-FILE=target/api-gateway-0.0.1.jar
COPY target/*.jar app_gateway.jar
ENTRYPOINT ["java", "-jar", "app_gateway.jar"]