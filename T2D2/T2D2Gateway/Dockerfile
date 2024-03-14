FROM openjdk:17-oracle

WORKDIR /

COPY target/T2D2Gateway-1.0.jar T2D2Gateway.jar
COPY config/application.properties application.properties

EXPOSE 9091

CMD java -jar T2D2Gateway.jar --spring.config.location=file:/application.properties