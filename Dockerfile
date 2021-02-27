# Maven build container 

FROM maven:3.6.3-openjdk-8 AS maven_build

COPY pom.xml /tmp/

COPY src /tmp/src/

WORKDIR /tmp/

RUN mvn package

#pull base image

FROM openjdk

#maintainer 
MAINTAINER manish.mrao@gmail.com
#expose port 8080
EXPOSE 8080

#default command
CMD java -jar /data/employeeapp.jar

#copy this app to docker image from builder image

COPY --from=maven_build /tmp/target/employeeapp.jar /data/employeeapp.jar
