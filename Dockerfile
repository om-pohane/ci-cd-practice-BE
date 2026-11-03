# ---------- Stage 1: build the jar ----------
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

# The cache mount keeps downloaded Maven dependencies between builds,
# so rebuilding after a code change is fast.
RUN --mount=type=cache,target=/root/.m2 mvn -B package -DskipTests


# ---------- Stage 2: run the jar ----------
FROM eclipse-temurin:17-jre
WORKDIR /app

# Run as a normal user instead of root
RUN useradd --system --create-home spring
USER spring

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=60.0", "-XX:+ExitOnOutOfMemoryError", "-jar", "app.jar"]
