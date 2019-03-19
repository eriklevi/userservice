FROM openjdk:8-jdk-alpine
MAINTAINER Erik Levi <levi.erik@gmail.com>
ADD target/userservice-0.0.1-SNAPSHOT.jar user.jar
ENTRYPOINT ["java", "-jar", "/user.jar"]
