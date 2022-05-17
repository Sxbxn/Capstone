FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/capstone-user-service-1.0.jar CapstoneUserService.jar
ENTRYPOINT ["java", "-jar", "CapstoneUserService.jar"]