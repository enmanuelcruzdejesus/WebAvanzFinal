# Archivo que representa la información para la creación de
# una imagen para Docker, indicando todos los parametros necesarios.

# El comando FROM indica la imagen base.
# openjdk:8-jdk-alpine es una imagen de Java 8 muy ligera.
# FROM openjdk:8-jdk-alpine
FROM openjdk:11-jre-slim


# Añadiendo el punto de montaje en el host
# Por defecto Tomcat crea los archivo temporales en esa ruta,
# lo habilitamos para ver los log, no es necesario para nuestro ejemplo.
VOLUME /tmp

# Puertos que estarán disponibles de nuestra aplicación.
EXPOSE 9090

# Copiando el archivo jar generado luego de la ejecución del comando
# gradle task bootjar, se crean el jar y se copia a la imagen.
COPY /build/libs/microservicio-usuario-0.0.1-SNAPSHOT.jar microservicio-usuario-0.0.1-SNAPSHOT.jar

ENV spring.datasource.url=jdbc:mysql://localhost:3306/usersdb
ENV spring.datasource.username=root
ENV spring.datasource.password=entrada01
ENV eureka.client.service-url.defaultZone=http://localhost:8761/eureka
# ENV spring.cloud.config.uri=http://localhost:8888/
#

#Comando que se ejecuta una vez es iniciada la aplicación.
ENTRYPOINT ["java", "-jar", "microservicio-usuario-0.0.1-SNAPSHOT.jar"]
