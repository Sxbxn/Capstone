FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY target/capstone-apigateway-service-1.0.jar CapstoneApigatewayService.jar
ENTRYPOINT ["java", "-jar", "CapstoneApigatewayService.jar"]