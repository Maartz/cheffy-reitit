FROM clojure:1.11.1-alpine AS build

WORKDIR /app

COPY . .

RUN lein deps
RUN lein uberjar


FROM openjdk:8-jre-alpine

WORKDIR /app

COPY --from=build /app/target/uberjar/cheffy.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]
