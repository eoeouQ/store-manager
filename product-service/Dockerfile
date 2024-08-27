FROM gradle:8.8-jdk17 AS build
WORKDIR /home/gradle
COPY ../product-service product-service
COPY ../store-manager-entities store-manager-entities
WORKDIR /home/gradle/product-service
RUN gradle clean build

FROM openjdk:17-jdk-slim
RUN mkdir /app
COPY --from=build /home/gradle/product-service/build/libs/*.jar /app/product-service.jar
CMD ["java", "-jar", "/app/product-service.jar"]