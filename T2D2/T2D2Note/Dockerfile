FROM openjdk:17-oracle

WORKDIR /

COPY target/T2D2Note-1.0.jar T2D2Note.jar
COPY config/application.properties application.properties

EXPOSE 8083

CMD java -jar T2D2Note.jar --spring.config.location=file:/application.properties