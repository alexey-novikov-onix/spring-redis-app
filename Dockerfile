FROM openjdk:8
ARG JAR=target/app-*.jar
COPY ${JAR} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]