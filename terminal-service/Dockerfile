FROM openjdk:17-oracle
EXPOSE 8081
ARG JAR_FILE=target/terminal-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} terminal-service.jar
ENTRYPOINT ["java","-jar","/terminal-service.jar"]


