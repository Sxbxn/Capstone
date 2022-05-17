FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/capstone-cell-service-1.0.jar CellService.jar
ENTRYPOINT ["java", "-jar", "CellService.jar"]