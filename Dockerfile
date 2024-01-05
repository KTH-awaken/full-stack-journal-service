FROM openjdk:17-jdk-slim

VOLUME /tmp

EXPOSE 8082

COPY target/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]

