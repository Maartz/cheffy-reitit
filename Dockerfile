FROM clojure:temurin-17-lein-alpine AS build

WORKDIR /app

COPY . .

RUN lein deps
RUN lein uberjar


FROM clojure:temurin-17-alpine
WORKDIR /app

COPY --from=build /app/target/cheffy.jar /app/app.jar

CMD ["java", "-jar", "/app/app.jar"]
