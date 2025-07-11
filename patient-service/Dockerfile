FROM openjdk:17-jdk-slim
ARG JAR-FILE=target/patient-service-0.0.1.jar
COPY target/*.jar app_patient.jar
ENTRYPOINT ["java", "-jar", "app_patient.jar"]