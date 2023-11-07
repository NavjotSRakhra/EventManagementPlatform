FROM ghcr.io/graalvm/graalvm-community:21 AS build

WORKDIR /app

COPY .mvn .mvn
COPY src src
COPY pom.xml .
COPY mvnw .

RUN chmod +x mvnw
RUN ./mvnw native:compile -Pnative

FROM scratch

WORKDIR /app

COPY --from=build /app/target/app .
RUN chmod +x app

ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["./app"]
