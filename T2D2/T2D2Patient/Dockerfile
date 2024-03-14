FROM openjdk:17-oracle

WORKDIR /

COPY target/T2D2Patient-1.0.jar T2D2Patient.jar
COPY config/application.properties application.properties

EXPOSE 8082

CMD java -jar T2D2Patient.jar --spring.config.location=file:/application.properties