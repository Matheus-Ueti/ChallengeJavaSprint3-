
FROM gradle:jdk17-alpine AS builder
WORKDIR /home/gradle/project

COPY --chown=gradle:gradle . .

RUN if [ -f ./gradlew ]; then ./gradlew --no-daemon clean bootJar -x test; else gradle --no-daemon clean bootJar -x test; fi


FROM eclipse-temurin:17-jre-jammy

ARG JAR_FILE=build/libs/*.jar

COPY --from=builder /home/gradle/project/build/libs/*.jar /app/app.jar

WORKDIR /app
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
