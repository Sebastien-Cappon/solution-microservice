FROM openjdk:17-oracle

WORKDIR /

COPY target/T2D2DiscoveryService-1.0.jar T2D2DiscoveryService.jar
COPY config/application.properties application.properties

EXPOSE 9090

CMD java -jar T2D2DiscoveryService.jar --spring.config.location=file:/application.properties