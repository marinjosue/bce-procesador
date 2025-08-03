# Usar una imagen base de OpenJDK 24 con Alpine Linux para menor tamaño
FROM openjdk:24-jdk-slim

# Instalar herramientas necesarias y limpiar cache
RUN apt-get update && apt-get install -y \
    curl \
    && rm -rf /var/lib/apt/lists/*

# Crear un usuario no-root para ejecutar la aplicación (seguridad)
RUN groupadd -r appuser && useradd -r -g appuser appuser

# Crear directorio de trabajo
WORKDIR /app

# Copiar archivos de configuración de Maven
COPY pom.xml ./
COPY mvnw ./
COPY mvnw.cmd ./
COPY .mvn .mvn

# Hacer ejecutable el script de Maven wrapper
RUN chmod +x mvnw

# Descargar dependencias (para aprovechar cache de Docker layers)
RUN ./mvnw dependency:go-offline -B

# Copiar el código fuente
COPY src ./src

# Compilar la aplicación
RUN ./mvnw clean package -DskipTests

# Crear directorio para logs
RUN mkdir -p /app/logs && chown -R appuser:appuser /app

# Cambiar al usuario no-root
USER appuser

# Exponer el puerto de la aplicación
EXPOSE 8082

# Configurar variables de entorno por defecto
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"
ENV SPRING_PROFILES_ACTIVE=prod

# Comando para ejecutar la aplicación
CMD ["sh", "-c", "java $JAVA_OPTS -jar target/bce-procesador-0.0.1-SNAPSHOT.jar"]
