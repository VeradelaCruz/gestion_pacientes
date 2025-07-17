FROM openjdk:17-jdk-slim
ARG JAR-FILE=target/appointment-service-0.0.1.jar
COPY target/*.jar app_appointment.jar
ENTRYPOINT ["java", "-jar", "app_appointment.jar"]