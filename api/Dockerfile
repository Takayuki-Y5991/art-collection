# Building Stage
FROM sbtscala/scala-sbt:eclipse-temurin-focal-17.0.9_9_1.9.8_3.3.1 as builder

WORKDIR /api

COPY ./build.sbt ./build.sbt
COPY ./project ./project

RUN sbt update

COPY ./src ./src
RUN sbt assembly

# Running App Stage
FROM eclipse-temurin:21.0.1_12-jre-jammy

WORKDIR /api

COPY --from=builder /api/target/scala-3.3.1/api.jar ./api.jar

CMD ["java", "-jar", "./api.jar"]