FROM gradle:9.5-jdk17 as build
WORKDIR /app
COPY . .
RUN ./gradlew clean build --no-daemon --refresh-dependencies

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar usuario.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/usuario.jar"]