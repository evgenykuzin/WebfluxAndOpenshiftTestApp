FROM openjdk:8-jdk-alpine
MAINTAINER Evgeny Kuzin <eikuzin@sberbank.ru>
#ADD installer/bicrypt /hybrid/bicrypt
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar","/app.jar"]
