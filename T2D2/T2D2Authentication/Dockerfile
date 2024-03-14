FROM openjdk:17-oracle

WORKDIR /

COPY target/T2D2Authentication-1.0.jar T2D2Authentication.jar
COPY config/application.properties application.properties

EXPOSE 8081

CMD java -jar T2D2Authentication.jar --spring.config.location=file:/application.properties