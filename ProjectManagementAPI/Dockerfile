FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY  --from=build /home/app/target/war_name.war app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
