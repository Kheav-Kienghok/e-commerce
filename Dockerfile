FROM maven:3.9.6-eclipse-temurin-21-alpine AS build

WORKDIR /src
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B package -DskipTests


FROM eclipse-temurin:21-jre-alpine

RUN apk add --no-cache dumb-init \
    && addgroup --system javauser \
    && adduser -S -s /bin/false -G javauser javauser

WORKDIR /app
COPY --from=build /src/target/e-commerce-0.0.1-SNAPSHOT.jar app.jar
RUN chown -R javauser:javauser /app

EXPOSE 8080

USER javauser
ENTRYPOINT ["dumb-init", "--"]
CMD ["java", "-jar", "app.jar"]
