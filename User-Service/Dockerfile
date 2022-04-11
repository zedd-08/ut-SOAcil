FROM adoptopenjdk:11-jre-hotspot

WORKDIR /app
COPY build/libs/user-0.0.1-SNAPSHOT.jar service.jar

ENV PORT 9093
EXPOSE 9093

ENTRYPOINT ["java","-jar","service.jar"]