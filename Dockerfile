FROM openjdk:17-jdk

WORKDIR /app

COPY target/news-0.0.1-SNAPSHOT.jar /app/news.jar

EXPOSE 8080

CMD ["java", "-jar", "news.jar"]

