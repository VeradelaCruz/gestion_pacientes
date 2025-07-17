FROM openjdk:17-jdk-slim
ARG JAR-FILE=target/config-server-0.0.1.jar
COPY target/*.jar app_config.jar
ENTRYPOINT ["java", "-jar", "app_config.jar"]