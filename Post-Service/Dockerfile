FROM adoptopenjdk:11-jre-hotspot

WORKDIR /app
COPY build/libs/postservice-0.0.1-SNAPSHOT.jar service.jar

ENV PORT 9092
EXPOSE 9092

ENTRYPOINT ["java","-jar","service.jar"]