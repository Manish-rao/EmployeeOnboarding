FROM openjdk:8
EXPOSE 8080
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} EmployeeApp.jar
ENTRYPOINT ["java","-jar","/EmployeeApp.jar"]
