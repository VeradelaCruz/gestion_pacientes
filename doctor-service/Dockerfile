FROM openjdk:17-jdk-slim
ARG JAR-FILE=target/doctor-service-0.0.1.jar
COPY ${JAR-FILE} app_doctor-service.jar
ENTRYPOINT ["java", "-jar", "app_eureka.jar"]