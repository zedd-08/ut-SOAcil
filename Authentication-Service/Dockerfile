FROM adoptopenjdk:11-jre-hotspot

WORKDIR /app
COPY build/libs/authentication-0.0.1-SNAPSHOT.jar service.jar

ENV PORT 9090
EXPOSE 9090

ENTRYPOINT ["java","-jar","service.jar"]