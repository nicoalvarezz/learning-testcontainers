FROM openjdk:17

COPY target/learning-testcontainers-1.0-SNAPSHOT.jar /data/learning-testcontainers-1.0-SNAPSHOT.jar

COPY config.yml /data/config.yml

CMD java -jar /data/learning-testcontainers-1.0-SNAPSHOT.jar server /data/config.yml

EXPOSE 8080
