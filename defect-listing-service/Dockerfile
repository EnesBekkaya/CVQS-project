FROM openjdk:17-oracle
EXPOSE 8089
ARG JAR_FILE=target/defect-listing-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} defect-listing-service.jar
ENTRYPOINT ["java","-jar","/defect-listing-service.jar"]


