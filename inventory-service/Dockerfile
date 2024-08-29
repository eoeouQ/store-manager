FROM gradle:8.8-jdk17 AS build
WORKDIR /home/gradle
COPY ../inventory-service inventory-service
COPY ../store-manager-entities store-manager-entities
WORKDIR /home/gradle/inventory-service
RUN gradle clean build

FROM openjdk:17-jdk-slim
RUN mkdir /app
COPY --from=build /home/gradle/inventory-service/build/libs/*.jar /app/inventory-service.jar
CMD ["java", "-jar", "/app/inventory-service.jar"]