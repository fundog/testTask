FROM openjdk:11
ADD target/email_server.jar email_server.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "email_server.jar"]