FROM ubuntu:latest AS build

ARG PORT=8080
ARG PROFILE=dev

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install 

FROM openjdk:17-jdk-slim

EXPOSE ${PORT}

COPY --from=build /target/jz-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=${PROFILE}  -DPORT=${PORT}","-jar","/app.jar"]