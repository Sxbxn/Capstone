FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/capstone-configure-server-1.0.jar CapstoneConfigServer.jar
ENTRYPOINT ["java", "-jar", "CapstoneConfigServer.jar"]