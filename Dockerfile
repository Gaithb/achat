FROM openjdk:17
COPY target/achat-1.9.jar /app/achat.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app/achat.jar"]
