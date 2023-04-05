FROM openjdk:17-oracle
EXPOSE 8888
ARG JAR_FILE=target/gateway-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} gateway.jar
ENTRYPOINT ["java","-jar","/gateway.jar"]


