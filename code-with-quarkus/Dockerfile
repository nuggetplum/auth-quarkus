# Stage 1: Build the application
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /build

# Copy pom.xml and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests -B

# Stage 2: Runtime image
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copy the built artifact from build stage
COPY --from=build /build/target/quarkus-app/lib/ /app/lib/
COPY --from=build /build/target/quarkus-app/*.jar /app/
COPY --from=build /build/target/quarkus-app/app/ /app/app/
COPY --from=build /build/target/quarkus-app/quarkus/ /app/quarkus/

# Create non-root user
RUN groupadd -r quarkus && useradd -r -g quarkus quarkus
RUN chown -R quarkus:quarkus /app
USER quarkus

# Expose port
EXPOSE 8080

# Set timezone
ENV TZ=Asia/Kolkata
ENV JAVA_OPTS="-Duser.timezone=Asia/Kolkata"

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
    CMD java -cp /app/lib/* io.quarkus.runner.GeneratedMain || exit 1

# Run the application
ENTRYPOINT ["java", "-Duser.timezone=Asia/Kolkata", "-jar", "/app/quarkus-run.jar"]
