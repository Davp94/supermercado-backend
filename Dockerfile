FROM maven:3.9.9-amazoncorretto-21-alpine AS build
WORKDIR /app
COPY ./pom.xml /app/pom.xml
COPY ./src/main/java/com/blumbit/hospital_service/HospitalServiceApplication.java /app/src/main/java/com/blumbit/hospital_service/HospitalServiceApplication.java

RUN mvn -f /app/pom.xml dependency:go-offline
COPY . /app
RUN mvn -f /app/pom.xml clean package

FROM amazoncorretto:21-alpine3.18
EXPOSE 8100
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar" ]