FROM openjdk:14-jdk-alpine
COPY target/lor-matches-1.0-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]