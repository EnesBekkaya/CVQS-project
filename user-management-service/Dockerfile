FROM openjdk:17-oracle
EXPOSE 8082
ARG JAR_FILE=target/user-management-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} user-management-service.jar
ENTRYPOINT ["java","-jar","/user-management-service.jar"]


