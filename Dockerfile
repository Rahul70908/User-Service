FROM openjdk:17

EXPOSE 8080

COPY build/libs/User-Service-1.0.1.jar User-Service-1.0.1.jar

ENTRYPOINT ["java", "-jar", "/User-Service-1.0.1.jar"]