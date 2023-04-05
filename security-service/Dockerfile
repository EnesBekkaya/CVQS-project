FROM openjdk:17-oracle
EXPOSE 9092
ARG JAR_FILE=target/security-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} security-service.jar
ENTRYPOINT ["java","-jar","/security-service.jar"]


