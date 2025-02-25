
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app


COPY pom.xml .
RUN apk add --no-cache maven
RUN mvn dependency:go-offline -B


COPY src ./src
RUN mvn clean package -DskipTests -B


FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=builder /app/target/Ticket_management-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
