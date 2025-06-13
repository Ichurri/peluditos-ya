# Imagen base con Java 17 JDK
FROM openjdk:17-jdk-slim

# Definir directorio de trabajo
WORKDIR /app

# Copiar el archivo JAR construido en target al contenedor
COPY target/*.jar app.jar

# Si usas variables de entorno en .env, mejor pasarlas en Azure App Service en configuración, no copiar el archivo
# COPY .env .env   --> Lo comento, no es buena práctica copiar .env en la imagen

# Comando para ejecutar la app
ENTRYPOINT ["java", "-jar", "app.jar"]
