FROM openjdk:17-slim
COPY ./build/libs/*.jar /app.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["sh", "-c", "java -Dspring.profiles.active=prod -jar /app.jar"]