FROM openjdk:17
EXPOSE 8082
ADD target/achat-1.9.jar /app/achat.jar
ENTRYPOINT ["java","-jar","/app/achat.jar"]
