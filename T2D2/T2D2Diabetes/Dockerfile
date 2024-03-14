FROM openjdk:17-oracle

WORKDIR /

COPY target/T2D2Diabetes-1.0.jar T2D2Diabetes.jar
COPY config/application.properties application.properties

EXPOSE 8084

CMD java -jar T2D2Diabetes.jar --spring.config.location=file:/application.properties