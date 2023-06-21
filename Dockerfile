FROM openjdk:19
WORKDIR /app
COPY target/rugram-0.0.1-SNAPSHOT.jar /app/rugram.jar
ENTRYPOINT ["java", "-jar", "rugram.jar"]