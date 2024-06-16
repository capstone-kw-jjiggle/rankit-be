FROM openjdk:17-slim
COPY --from=gradle:7.4.0-jdk17 /home/gradle/src/build/libs/*.jar /app.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=prod -jar /app.jar"]
