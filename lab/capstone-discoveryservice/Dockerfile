FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/capstone-discoveryservice-1.0.jar CapstoneDiscoveryService.jar
ENTRYPOINT ["java", "-jar", "CapstoneDiscoveryService.jar"]