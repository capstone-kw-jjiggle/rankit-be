FROM openjdk:17-slim
COPY --from=build /home/gradle/src/build/libs/*.jar /app.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=prod -jar /app.jar"]
