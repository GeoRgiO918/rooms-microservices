FROM amazoncorretto:17

WORKDIR /usr/src/roomservice

COPY target/room-service.jar /usr/src/roomservice



EXPOSE 8080

CMD ["java", "-jar", "room-service.jar"]
