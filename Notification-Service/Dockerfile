FROM adoptopenjdk:11-jre-hotspot

WORKDIR /app
COPY build/libs/Notification-Service-0.0.1-SNAPSHOT.jar service.jar

ENV PORT 9091
EXPOSE 9091

ENTRYPOINT ["java","-jar","service.jar"]