FROM openjdk:8
EXPOSE 8080:8080
ADD target/statistics-0.0.1-SNAPSHOT.jar statistics.jar
ENTRYPOINT ["java", "-jar", "statistics.jar"]
