FROM openjdk:17

COPY target/learning-testcontainers-1.0-SNAPSHOT.jar /data/learning-testcontainers-1.0-SNAPSHOT.jar

COPY empty.yml /data/empty.yml

CMD java -jar /data/learning-testcontainers-1.0-SNAPSHOT.jar server /data/empty.yml

EXPOSE 8080
