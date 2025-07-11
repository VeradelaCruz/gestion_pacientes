FROM openjdk:17-jdk-slim
ARG JAR-FILE=target/eureka-service-0.0.1.jar
COPY ${JAR-FILE} app_eureka.jar
ENTRYPOINT ["java", "-jar", "app_eureka.jar"]