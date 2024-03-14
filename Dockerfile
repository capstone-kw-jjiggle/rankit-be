FROM gradle:7.4.0-jdk17-alpine AS build
COPY . /home/gradle/src
WORKDIR /home/gradle/src
ARG JAR_FILE=build/libs/*.jar

FROM openjdk:17-alpine
COPY --from=build /home/gradle/src/build/libs/*.jar /app.jar
ARG JAVA_ACTIVE=-Dspring.profiles.active=docker
ENTRYPOINT ["sh", "-c", "java ${JAVA_ACTIVE} -jar /app.jar"]


