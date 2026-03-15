FROM maven:3.9.5-eclipse-temurin-21 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-jammy
COPY --from=build /target/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-Xmx300m", "-Xms300m", "-jar", "app.jar"]