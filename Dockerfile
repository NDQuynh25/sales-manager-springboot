
#Step 1: Build the Java application using Maven

# Use the official Maven image with Amazon Corretto 21
FROM maven:3.9.8-amazoncorretto-21 AS build

# copy pom.xml, src file to /app folder
WORKDIR /app
COPY pom.xml .
COPY src ./src

# build source code with maven
RUN mvn clean package -DskipTests


# Step 2: Create the runtime image using Amazon Corretto 21
FROM amazoncorretto:21.0.4
# Set the working directory
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Command to run the application
CMD ["java", "-jar", "app.jar"]

# docker build -t e-commerce:0.0.1 .