# apimeli
API de geolocalización de IP's para proyecto MeLi.

Imagen archivo docker:

# Usa una imagen base de Java
FROM openjdk:17-jdk-alpine

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR de la aplicación al contenedor
COPY target/localizarip.jar localizarip.jar

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "localizarip.jar"]
