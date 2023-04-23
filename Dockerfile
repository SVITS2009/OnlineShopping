FROM openjdk:11-jdk-slim
WORKDIR /app
COPY target/onlineShopping.jar /app/onlineShopping.jar
ENV KAFKA_BOOTSTRAP_SERVERS=localhost:9092
EXPOSE 8081
CMD ["java", "-jar", "-Dspring.kafka.bootstrap-servers=${KAFKA_BOOTSTRAP_SERVERS}", "onlineShopping.jar"]
