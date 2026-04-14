# Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:21

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Expose the default application port
EXPOSE 8080

# Compile the Java application
RUN javac -d out $(find src -name "*.java")

# Set the HEADLESS environment variable to true
ENV HEADLESS=true

# Command to run the application
CMD ["java", "-cp", "out", "src.App"]
