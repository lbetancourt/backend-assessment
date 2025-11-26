# ----------------------------------------------------------------------
# STAGE 1: BUILD (Compila la aplicación usando Gradle)
# ----------------------------------------------------------------------
FROM gradle:8.5-jdk17-alpine AS build
LABEL maintainer="luis betancourt"

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia los archivos de configuración de Gradle (build.gradle, settings.gradle)
# y el Gradle Wrapper para aprovechar el cache.
COPY build.gradle settings.gradle ./
COPY gradlew .
COPY gradle ./gradle

# Copia el código fuente
COPY src ./src

# Opcional: Si usas Spring Boot 3.x, puedes copiar también el Dockerfile para que
# Gradle lo incluya en la imagen (aunque generalmente no es necesario para el JAR).
# COPY Dockerfile .

# Construye la aplicación. 'bootJar' es la tarea de Spring Boot para generar el JAR ejecutable.
RUN ./gradlew clean bootJar --no-daemon

# ----------------------------------------------------------------------
# STAGE 2: RUN (Crea la imagen final, ligera y solo con el JRE)
# ----------------------------------------------------------------------
# Usar una imagen JRE ligera y segura.
FROM eclipse-temurin:17-jre-alpine

# Define el puerto que expone la aplicación (8080 por defecto en Spring Boot)
EXPOSE 8080

# Define un usuario no root para mejorar la seguridad
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

# Crea el directorio de trabajo
WORKDIR /app

# Copia el JAR generado en la etapa 'build' a la imagen final.
# El JAR de Spring Boot se encuentra en build/libs/
ARG JAR_FILE=build/libs/*.jar
COPY --from=build /app/${JAR_FILE} app.jar

# Comando de entrada para ejecutar la aplicación
# El formato 'exec' es preferido para el manejo correcto de señales.
ENTRYPOINT ["java", "-jar", "app.jar"]