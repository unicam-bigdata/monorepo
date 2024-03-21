FROM gradle:7.6-jdk AS build
WORKDIR /app
COPY src/main ./src/main
COPY build.gradle settings.gradle  ./

RUN gradle clean build


FROM openjdk:17.0.1-jdk-slim AS run

RUN adduser --system --group app-user


COPY --from=build --chown=app-user:app-user ./app/build/libs/ ./app/
WORKDIR /app

EXPOSE 8080
USER app-user

CMD ["java", "-jar", "backend-1.0.0-VIEW#BIGDATA.jar"]