# Fase de construcción del JAR
FROM maven:3.8.5-openjdk-17 AS build

WORKDIR /app

# 1. Copia solo el POM primero para aprovechar la caché de Maven
COPY pom.xml .
# Descarga dependencias (se cachea si el POM no cambia)
RUN mvn dependency:go-offline -B

# 2. Copia todo el código fuente y recursos
COPY src ./src

# 3. Construye el JAR
RUN mvn clean package -DskipTests

# Fase de ejecución
FROM openjdk:17-jdk-slim
EXPOSE 8080

# Copia el JAR generado usando el nombre exacto
COPY --from=build /app/target/MedicationDosageApp.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]