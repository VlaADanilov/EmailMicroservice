FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/EmailMicroservice-0.0.1-SNAPSHOT.jar app.jar

CMD ["java", "-jar", "app.jar"]