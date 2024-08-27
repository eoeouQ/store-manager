FROM gradle:8.8-jdk17 AS build
WORKDIR /home/gradle
COPY ../order-service order-service
COPY ../store-manager-entities store-manager-entities
WORKDIR /home/gradle/order-service
RUN gradle clean build

FROM openjdk:17-jdk-slim
RUN mkdir /app
COPY --from=build /home/gradle/order-service/build/libs/*.jar /app/order-service.jar
CMD ["java", "-jar", "/app/order-service.jar"]