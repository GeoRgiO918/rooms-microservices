FROM amazoncorretto:17

WORKDIR /usr/src/auth-service

COPY target/auth-service.jar /usr/src/auth-service



EXPOSE 8083

CMD ["java", "-jar", "auth-service.jar"]
