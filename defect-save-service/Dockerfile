FROM openjdk:17-oracle
EXPOSE 8089
ARG JAR_FILE=target/error-save-service-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} defect_save_servcie.jar
ENTRYPOINT ["java","-jar","/defect_save_servcie.jar"]


