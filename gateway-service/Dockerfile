FROM gradle:7.6.4-jdk17 AS build
COPY --chown=gradle:gradle .. /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build -x test

FROM openjdk:17-jdk-slim
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/gateway-service.jar
CMD ["java", "-jar", "/app/gateway-service.jar"]
