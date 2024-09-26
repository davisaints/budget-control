FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/budget-control.jar budget-control.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "budget-control.jar"]