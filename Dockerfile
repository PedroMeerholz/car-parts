FROM ubuntu:22.04

WORKDIR /home/stock-api

RUN apt update && apt upgrade -y

RUN apt install openjdk-17-jdk -y

RUN apt install maven -y

COPY /src /home/stock-api/src

COPY ./pom.xml /home/stock-api

RUN mvn clean package -Dmaven.test.skip

EXPOSE 8080

ENTRYPOINT ["mvn", "spring-boot:run"]