#
# Build stage
#
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/guideline-0.0.1-SNAPSHOT.jar guideline.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","guideline.jar"]

FROM mysql:latest
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=guideline
EXPOSE 3306
