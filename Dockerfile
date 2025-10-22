# --- Build lightweight Java 17 image ---
FROM openjdk:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy the built jar
COPY target/XpenseSplit-0.0.1-SNAPSHOT.jar app.jar

# Copy .env file (optional, for reference — Docker will use it if passed via --env-file)
COPY .env .env

# Expose the application port
EXPOSE 8080

# Load environment variables and start the app
CMD ["sh", "-c", "export $(grep -v '^#' .env | xargs) && java -jar app.jar"]
