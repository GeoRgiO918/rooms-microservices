FROM amazoncorretto:17

WORKDIR /usr/src/fileservice

COPY target/file-service.jar /usr/src/fileservice



EXPOSE 8082

CMD ["java", "-jar", "file-service.jar"]
