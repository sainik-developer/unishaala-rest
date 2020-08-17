FROM openjdk:8-jre-slim
EXPOSE 9000
RUN mkdir /app
COPY build/libs/*.jar /app/spring-boot-rest-unishaala-backend.jar
ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/spring-boot-rest-unishaala-backend.jar"]
