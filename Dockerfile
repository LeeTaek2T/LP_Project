#FROM ubuntu:latest
#LABEL authors="itaek"
#
#ENTRYPOINT ["top", "-b"]
FROM openjdk:17-jdk-slim
WORKDIR /app
ARG JAR_FILE=./*.jar
COPY ${JAR_FILE} b_app.jar
ENTRYPOINT ["java", "-jar", "b_app.jar"]