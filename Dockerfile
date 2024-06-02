# Step 1: jdk17 alpine 버전으로 하면 안됨! Random nextInt(?,?) 지원안함.
FROM gradle:7.4.0-jdk17 AS build
COPY . /home/gradle/src
WORKDIR /home/gradle/src
ARG JAR_FILE=build/libs/*.jar

# Step 2: 이미지 활용해서 진행
FROM openjdk:17-slim
COPY --from=build /home/gradle/src/build/libs/*.jar /app.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=prod -jar /app.jar"]
