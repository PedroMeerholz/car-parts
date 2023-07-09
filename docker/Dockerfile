FROM ubuntu:latest

RUN apt-get -y update
RUN apt install -y openjdk-17-jdk

RUN apt-get install -y wget
RUN apt-get install -y curl
RUN apt install -y nano

RUN wget https://mirrors.estointernet.in/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz
RUN tar -xvf apache-maven-3.6.3-bin.tar.gz
RUN mv apache-maven-3.6.3 /opt/
RUN M2_HOME='/opt/apache-maven-3.6.3'
RUN PATH="$M2_HOME/bin:$PATH"
RUN export PATH

RUN wget https://dlcdn.apache.org/tomcat/tomcat-8/v8.5.90/bin/apache-tomcat-8.5.90.tar.gz
RUN tar -xzf apache-tomcat-8.5.90.tar.gz
RUN mkdir /opt/tomcat
RUN mv apache-tomcat-8.5.90/* /opt/tomcat/
COPY ./files/tomcat-users.xml /opt/tomcat/conf/
COPY ./files/context.xml /opt/tomcat/webapps/manager/META-INF/