FROM openjdk:17-jdk-slim
ARG JAR-FILE=target/doctor-service-0.0.1.jar
COPY target/*.jar app_doctor.jar
ENTRYPOINT ["java", "-jar", "app_doctor.jar"]
