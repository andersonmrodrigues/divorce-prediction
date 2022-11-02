FROM adoptopenjdk/openjdk11:alpine AS build

COPY ADReNA_API.jar .
RUN mvn install:install-file -Dfile=ADReNA_API.jar -DgroupId=ADReNA_API -DartifactId=ADReNA_API -Dversion=1.0 -Dpackaging=jar


MAINTAINER AndersonMr
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]