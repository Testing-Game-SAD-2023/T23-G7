FROM openjdk:17
ARG JAR_FILE=target/testgame-1.0-alpha.jar
COPY ${JAR_FILE} g7.jar
EXPOSE 8080
ENTRYPOINT {"java","-jar","/g7.jar"}