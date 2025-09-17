FROM amazoncorretto:17

WORKDIR /usr/src/file-service

COPY target/file-service.jar /usr/src/file-service



EXPOSE 8082

CMD ["java", "-jar", "file-service.jar"]
