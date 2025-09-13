FROM amazoncorretto:17

WORKDIR /usr/src/fileservice

COPY target/room-service.jar /usr/src/fileservice



EXPOSE 8082

CMD ["java", "-jar", "file-service.jar"]
