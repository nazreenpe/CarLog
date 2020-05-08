FROM openjdk:11

COPY build/libs/carLog-*.jar /app.jar

CMD ["java", "-jar", "/app.jar"]